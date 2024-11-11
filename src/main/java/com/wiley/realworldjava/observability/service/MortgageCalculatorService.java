package com.wiley.realworldjava.observability.service;

public interface MortgageCalculatorService {
  double payment(double principal,
                 double annualInterestRate, int years);
}
