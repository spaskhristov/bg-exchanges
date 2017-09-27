package com.gmail.spaskhristov.exchange.model;

public class BNBfixing {

  private CurrencyType type;
  private String rateReference;

  public BNBfixing(CurrencyType type, String rateReference) {
    this.type = type;
    this.rateReference = rateReference;
  }

  public CurrencyType getType() {
    return type;
  }

  public void setType(CurrencyType type) {
    this.type = type;
  }

  public String getRateReference() {
    return rateReference;
  }

  public void setRateReference(String rateReference) {
    this.rateReference = rateReference;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[" + type + ", " + rateReference + "]");
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
    BNBfixing other = (BNBfixing) obj;
    if (type != other.type) {
      return false;
    }
    return true;
  }

}
