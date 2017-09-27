package com.gmail.spaskhristov.exchange.model;

public class Currency {

  private CurrencyType type;
  private String rateBuy;
  private String rateSell;

  public Currency(CurrencyType type, String rateBuy, String rateSell) {
    this.type = type;
    this.rateBuy = rateBuy;
    this.rateSell = rateSell;
  }

  public CurrencyType getName() {
    return type;
  }

  public void setName(CurrencyType type) {
    this.type = type;
  }

  public String getRateBuy() {
    return rateBuy;
  }

  public void setRateBuy(String rateBuy) {
    this.rateBuy = rateBuy;
  }

  public String getRateSell() {
    return rateSell;
  }

  public void setRateSell(String rateSell) {
    this.rateSell = rateSell;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[" + type + ", " + rateBuy + ", " + rateSell + "]");
    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Currency other = (Currency) obj;
    if (type != other.type) {
      return false;
    }
    return true;
  }

}
