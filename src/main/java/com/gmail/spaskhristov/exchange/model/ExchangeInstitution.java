package com.gmail.spaskhristov.exchange.model;

import java.util.HashSet;
import java.util.Set;

public class ExchangeInstitution {

  private Set<Currency> setCurrency;
  private String name;
  private String nameEn;
  private ExchangeInstitutionType type;
  private String urlStr;
  private int id;

  public ExchangeInstitution(int id, String name, String nameEn, String urlStr) {
    this.id = id;
    this.name = name;
    this.nameEn = nameEn;
    this.type = ExchangeInstitutionType.bank;
    this.urlStr = urlStr;
    this.setCurrency = new HashSet<Currency>();
  }

  public ExchangeInstitution(int id, String name, String nameEn, String urlStr,
      ExchangeInstitutionType type) {
    this.id = id;
    this.name = name;
    this.nameEn = nameEn;
    this.urlStr = urlStr;
    this.type = type;
    this.setCurrency = new HashSet<Currency>();
  }

  public String buy(String currType) {
    for (Currency curr : setCurrency) {
      if (curr.getName().name().equals(currType)) {
        return String.format("%.5f", Float.parseFloat(curr.getRateBuy()));
      }
    }
    return " - ";
  }

  public String sell(String currType) {
    for (Currency curr : setCurrency) {
      if (curr.getName().name().equals(currType)) {
        return String.format("%.5f", Float.parseFloat(curr.getRateSell()));
      }
    }
    return " - ";
  }

  public Set<Currency> getSetCurrency() {
    return setCurrency;
  }

  public void setSetCurrency(Set<Currency> setCurrency) {
    this.setCurrency = setCurrency;
  }

  public boolean addSetCurrency(Set<Currency> setCurrency) {
    return this.setCurrency.addAll(setCurrency);
  }

  public String getName() {
    return name;
  }

  public void setNameInstitution(String nameInstitution) {
    this.name = nameInstitution;
  }

  public ExchangeInstitutionType getType() {
    return type;
  }

  public void setType(ExchangeInstitutionType type) {
    this.type = type;
  }

  public String getUrlStr() {
    return urlStr;
  }

  public void setUrlStr(String urlStr) {
    this.urlStr = urlStr;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    ExchangeInstitution other = (ExchangeInstitution) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name + ": \n");
    for (Currency currency : setCurrency) {
      sb.append(currency.toString() + "\n");
    }
    return sb.toString();
  }

}
