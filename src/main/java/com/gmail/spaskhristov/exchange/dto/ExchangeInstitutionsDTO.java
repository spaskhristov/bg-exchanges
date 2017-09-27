package com.gmail.spaskhristov.exchange.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExchangeInstitutionsDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private boolean isBuy;
  private String minRate;
  private String maxRate;
  private List<ExchangeInstitutionDTO> institutionsClient;

  public ExchangeInstitutionsDTO() {
    institutionsClient = new ArrayList<ExchangeInstitutionDTO>();
  }

  public List<ExchangeInstitutionDTO> getInstitutionsClient() {
    return institutionsClient;
  }

  public void setInstitutionsClient(List<ExchangeInstitutionDTO> institutionsClient) {
    this.institutionsClient = institutionsClient;
  }

  public void add(ExchangeInstitutionDTO exchangeInstitutionClient) {
    institutionsClient.add(exchangeInstitutionClient);
  }

  public boolean isBuy() {
    return isBuy;
  }

  public void setBuy(boolean isBuy) {
    this.isBuy = isBuy;
  }

  public String getMinRate() {
    return minRate;
  }

  public void setMinRate(String minRate) {
    this.minRate = minRate;
  }

  public String getMaxRate() {
    return maxRate;
  }

  public void setMaxRate(String maxRate) {
    this.maxRate = maxRate;
  }

}
