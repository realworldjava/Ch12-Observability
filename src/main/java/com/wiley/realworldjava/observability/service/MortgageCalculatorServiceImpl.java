package com.wiley.realworldjava.observability.service;


import com.wiley.realworldjava.observability.exception.MortgageException;
import org.springframework.stereotype.Service;

@Service
public class MortgageCalculatorServiceImpl implements MortgageCalculatorService {

  @Override
  public double payment(double principal,
                        double annualInterestRate, int termInYears) {
    if(termInYears <=0) {
      throw new MortgageException("Years must be positive");
    }
    double monthlyInterestRate = annualInterestRate / 12 / 100;
    int numberOfPayments = termInYears * 12;

    // mortgage formula: P * R * [(1+R)^N] / [(1+R)^N - 1]
    return principal * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
       / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
  }
}
