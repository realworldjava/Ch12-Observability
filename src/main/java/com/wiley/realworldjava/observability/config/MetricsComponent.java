package com.wiley.realworldjava.observability.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetricsComponent {

  private final Counter hitCounter;
  private final AtomicInteger sampleGauge;
  private final Timer sampleHistogram;
  private final DistributionSummary sampleSummary;

  @Autowired
  public MetricsComponent(MeterRegistry meterRegistry) {
    // Counter
    this.hitCounter = Counter.builder("sample_counter")
       .description("A sample counter")
       .register(meterRegistry);

    // Gauge
    this.sampleGauge = new AtomicInteger(0);
    Gauge.builder("sample_gauge", sampleGauge, AtomicInteger::get)
       .description("A sample gauge")
       .register(meterRegistry);

    // Histogram (using Timer)
    this.sampleHistogram = Timer.builder("sample_histogram")
       .description("A sample histogram")
       .publishPercentileHistogram()
       .register(meterRegistry);

    // Summary
    this.sampleSummary = DistributionSummary.builder("sample_summary")
       .description("A sample summary")
       .register(meterRegistry);
  }

  public void incrementCounter() {
    hitCounter.increment();
  }

  public void setGaugeValue(int value) {
    sampleGauge.set(value);
  }

  public void recordHistogram(long duration) {
    sampleHistogram.record(duration, TimeUnit.SECONDS);
  }

  public void recordSummary(double amount) {
    sampleSummary.record(amount);
  }
}

