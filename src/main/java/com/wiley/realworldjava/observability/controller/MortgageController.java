package com.wiley.realworldjava.observability.controller;


import com.wiley.realworldjava.observability.Ch12ObservabilityApplication;
import com.wiley.realworldjava.observability.Mortgage;
import com.wiley.realworldjava.observability.Response;
import com.wiley.realworldjava.observability.service.MortgageCalculatorServiceImpl;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("/mtg")
public class MortgageController {
  @Autowired
  private MortgageCalculatorServiceImpl mortgageCalculator;
  private final Logger logger = LoggerFactory.getLogger(Ch12ObservabilityApplication.class);
  private Counter hitCounter;
  private Long duration;
  private final Timer histogramTimer;
  private Random random = new Random();

  public MortgageController(MeterRegistry meterRegistry) {
    this.hitCounter = Counter.builder("payments_get_counter")
       .description("GET payment counter")
       .register(meterRegistry);

    Gauge.builder("payments_query_duration_gauge", this::getDuration)
       .description("Duration of payment queries in seconds.")
       .register(meterRegistry);

    this.histogramTimer  = Timer.builder("payments_query_duration_seconds")
       .serviceLevelObjectives(
          Duration.of(500, ChronoUnit.MILLIS),
          Duration.of(1000, ChronoUnit.MILLIS),
          Duration.of(1500, ChronoUnit.MILLIS),
          Duration.of(2000, ChronoUnit.MILLIS),
          Duration.of(3000, ChronoUnit.MILLIS),
          Duration.of(5000, ChronoUnit.MILLIS),
          Duration.of(10000, ChronoUnit.MILLIS)
       ) // Adjust your buckets as needed
       .publishPercentileHistogram()
       .register(meterRegistry);
  }

  @PostMapping("/payment")
  public ResponseEntity<Response> calculateMonthlyPayment(
     @RequestBody List<Mortgage> mortgages)
     throws InterruptedException {
    LocalDateTime start = LocalDateTime.now();
    long time = -1;
    try {
      logger.info("Called payment POST endpoint " + mortgages);
      for(Mortgage mortgage : mortgages) {
        double principal = mortgage.getPrincipal();
        double rate = mortgage.getInterest();
        int years = mortgage.getYears();
        double payment = mortgageCalculator.payment(
           principal, rate, years);
        mortgage.setPayment(payment);
      }
      long delaySecs = random.nextInt(1, 5);
      logger.info("Delay secs:" + delaySecs);
      Thread.sleep(Duration.ofSeconds(delaySecs));
      HttpHeaders headers = new HttpHeaders();
      LocalDateTime end = LocalDateTime.now();
      time = start.until(end, ChronoUnit.SECONDS);
      // Set the value for our gauge
      setDuration(Duration.between(start, end).get(ChronoUnit.MILLIS));
      headers.add("Request time", "Call for payment completed at " + end);
      return new ResponseEntity<>(new Response(mortgages, end), headers, HttpStatus.OK);
    } finally {
      // set the value for our histogram
      histogramTimer.record(time, TimeUnit.SECONDS);
    }
  }

  @GetMapping("/payment")
  public ResponseEntity<String> calculateMonthlyPayment(
     @RequestParam double principal, @RequestParam int years,
     @RequestParam double interest) {
    hitCounter.increment();
    double payment = mortgageCalculator.payment(
       principal, interest, years);
    String rval = String.format("Principal:%,.2f<br>Interest: %.2f<br>" +
       "Years: %d<br>Monthly Payment:%.2f", principal, interest,
       years, payment);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Request time", "Call for payment at "
       + LocalDateTime.now());
    return new ResponseEntity<>(rval, headers, HttpStatus.OK);
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  private Long getDuration() {
    return duration;
  }

  @GetMapping("/hello")
  public String hello() {
    return LocalDateTime.now() + ": Hello, World ";
  }
}
