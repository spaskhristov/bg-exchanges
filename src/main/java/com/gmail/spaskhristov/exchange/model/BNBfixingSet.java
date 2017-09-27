package com.gmail.spaskhristov.exchange.model;

import java.util.HashSet;
import java.util.Set;

public class BNBfixingSet {

  private Set<BNBfixing> fixingBNB;

  public BNBfixingSet() {
    this.fixingBNB = new HashSet<BNBfixing>();
  }

  public BNBfixingSet(Set<BNBfixing> fixingBNB) {
    this.fixingBNB = fixingBNB;
  }

  public Set<BNBfixing> getFixingBNB() {
    return fixingBNB;
  }

  public void setFixingBNB(Set<BNBfixing> fixingBNB) {
    this.fixingBNB = fixingBNB;
  }

  public String reference(String currType) {
    for (BNBfixing fixing : fixingBNB) {
      if (fixing.getType().name().equals(currType)) {
        return fixing.getRateReference();
      }
    }
    return " - ";
  }

}
