package com.wiley.realworldjava.observability;

public class Mortgage {
  private double principal;
  private int years;
  private double interest;
  private User user;
  private double payment;

  public double getPrincipal() {
    return principal;
  }

  public void setPrincipal(double principal) {
    this.principal = principal;
  }

  public int getYears() {
    return years;
  }

  public void setYears(int years) {
    this.years = years;
  }

  public double getInterest() {
    return interest;
  }

  public void setInterest(double interest) {
    this.interest = interest;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("Mortgage: Principal:%,.2f<br>Interest: %.2f<br>Years: %d<br>", principal, interest, years);
  }

  public void setPayment(double payment) {
    this.payment = payment;
  }

  public double getPayment() {
    return payment;
  }
}
