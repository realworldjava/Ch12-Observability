package com.wiley.realworldjava.observability;

import java.time.LocalDateTime;
import java.util.List;

public class Response {
  private List<Mortgage> mortgages;
  private final LocalDateTime now;

  public Response(List<Mortgage> mortgages, LocalDateTime now) {
    this.mortgages = mortgages;
    this.now = now;
  }

  public List<Mortgage> getMortgages() {
    return mortgages;
  }

  public void setMortgages(List<Mortgage> mortgages) {
    this.mortgages = mortgages;
  }

  public LocalDateTime getNow() {
    return now;
  }

  @Override
  public String toString() {
    return "Response{" +
       "mortgages=" + mortgages +
       ", now=" + now +
       '}';
  }
}
