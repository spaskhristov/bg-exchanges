package com.gmail.spaskhristov.exchange.dto;

import java.io.Serializable;

import com.gmail.spaskhristov.exchange.model.ExchangeInstitutionType;

public class ExchangeInstitutionDTO implements Comparable<ExchangeInstitutionDTO>, Serializable {
  private static final long serialVersionUID = 1L;

  private String rateForOne;
  private String rate;
  private String name;
  private String nameEn;
  private String url;
  private ExchangeInstitutionType type;

  public ExchangeInstitutionDTO() {}

  public String getRateForOne() {
    return rateForOne;
  }

  public void setRateForOne(String rateForOne) {
    this.rateForOne = rateForOne;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ExchangeInstitutionType getType() {
    return type;
  }

  public void setType(ExchangeInstitutionType type) {
    this.type = type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rateForOne == null) ? 0 : rateForOne.hashCode());
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
    ExchangeInstitutionDTO other = (ExchangeInstitutionDTO) obj;
    if (rateForOne == null) {
      if (other.rateForOne != null) {
        return false;
      }
    } else if (!rateForOne.equals(other.rateForOne)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(ExchangeInstitutionDTO simplyObjectInst) {
    Float frateForOne = Float.parseFloat(rateForOne);
    Float fcurrRate = Float.parseFloat(simplyObjectInst.rateForOne);
    if (frateForOne > fcurrRate) {
      return -1;
    } else if (frateForOne < fcurrRate) {
      return 1;
    }
    return 0;
  }

}
