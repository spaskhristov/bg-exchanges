package com.gmail.spaskhristov.exchange.utils;

import java.util.Collections;
import java.util.List;

import com.gmail.spaskhristov.exchange.dto.ExchangeInstitutionDTO;
import com.gmail.spaskhristov.exchange.dto.ExchangeInstitutionsDTO;
import com.gmail.spaskhristov.exchange.impl.Exchange;
import com.gmail.spaskhristov.exchange.model.Currency;
import com.gmail.spaskhristov.exchange.model.CurrencyType;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;

public class CurrencyCalc {

  public CurrencyCalc() {}

  public static ExchangeInstitutionsDTO calcCurrency(Exchange exchange,
      CurrencyType fromCurrType, CurrencyType toCurrType, float rate) {
    boolean isExchangeFromBGNtoOther = false;
    if (fromCurrType.equals(CurrencyType.BGN)) {
      isExchangeFromBGNtoOther = true;
    }
    ExchangeInstitutionsDTO institutionsClient = new ExchangeInstitutionsDTO();
    CurrencyType currType = fromCurrType;
    if (isExchangeFromBGNtoOther) {
      currType = toCurrType;
    }
    List<ExchangeInstitution> institutions = exchange.getInstitutions();
    if (isCurrencyTypeOther(currType)) {
      institutions = exchange.getInstitutionsOther();
    }
    Float currMinRate = Float.MAX_VALUE;
    Float currMaxRate = Float.MIN_VALUE;
    for (ExchangeInstitution inst : institutions) {
      for (Currency curr : inst.getSetCurrency()) {
        if (curr.getName().equals(currType)) {
          ExchangeInstitutionDTO institutionClient = new ExchangeInstitutionDTO();
          institutionClient.setName(inst.getName());
          institutionClient.setUrl(inst.getUrlStr());
          institutionClient.setType(inst.getType());
          institutionClient.setNameEn(inst.getNameEn());
          if (isExchangeFromBGNtoOther) {
            Float currRateSell = rate / Float.parseFloat(curr.getRateSell());
            if (currMaxRate < currRateSell) {
              currMaxRate = currRateSell;
            }
            if (currMinRate > currRateSell) {
              currMinRate = currRateSell;
            }
            institutionClient.setRate(String.format("%.5f", currRateSell));
            institutionClient
                .setRateForOne(String.format("%.5f", 1 / Float.parseFloat(curr.getRateSell())));
          } else {
            Float currRateBuy = Float.parseFloat(curr.getRateBuy()) * rate;
            if (currMaxRate < currRateBuy) {
              currMaxRate = currRateBuy;
            }
            if (currMinRate > currRateBuy) {
              currMinRate = currRateBuy;
            }
            institutionClient.setRate(String.format("%.5f", currRateBuy));
            institutionClient
                .setRateForOne(String.format("%.5f", Float.parseFloat(curr.getRateBuy())));
          }
          institutionsClient.add(institutionClient);
        }
      }
    }
    String minRate = String.format("%.5f", currMinRate);
    String maxRate = String.format("%.5f", currMaxRate);
    institutionsClient.setMinRate(minRate);
    institutionsClient.setMaxRate(maxRate);
    institutionsClient.setBuy(false);
    Collections.sort(institutionsClient.getInstitutionsClient());
    return institutionsClient;
  }

  public static ExchangeInstitutionsDTO calcCurrencyBuy(Exchange exchange,
      CurrencyType buyCurrType, float rateBuy) {
    ExchangeInstitutionsDTO institutionsClient = new ExchangeInstitutionsDTO();
    Float currMinRate = Float.MAX_VALUE;
    Float currMaxRate = Float.MIN_VALUE;
    List<ExchangeInstitution> institutions = exchange.getInstitutions();
    if (isCurrencyTypeOther(buyCurrType)) {
      institutions = exchange.getInstitutionsOther();
    }
    for (ExchangeInstitution inst : institutions) {
      for (Currency curr : inst.getSetCurrency()) {
        if (curr.getName().equals(buyCurrType)) {
          ExchangeInstitutionDTO institutionClient = new ExchangeInstitutionDTO();
          institutionClient.setName(inst.getName());
          institutionClient.setUrl(inst.getUrlStr());
          institutionClient.setType(inst.getType());
          institutionClient.setNameEn(inst.getNameEn());
          Float currRateSell = Float.parseFloat(curr.getRateSell()) * rateBuy;
          if (currMaxRate < currRateSell) {
            currMaxRate = currRateSell;
          }
          if (currMinRate > currRateSell) {
            currMinRate = currRateSell;
          }
          institutionClient.setRate(String.format("%.5f", currRateSell));
          institutionClient
              .setRateForOne(String.format("%.5f", Float.parseFloat(curr.getRateSell())));
          institutionsClient.add(institutionClient);
        }
      }
    }
    String minRate = String.format("%.5f", currMinRate);
    String maxRate = String.format("%.5f", currMaxRate);
    institutionsClient.setMinRate(minRate);
    institutionsClient.setMaxRate(maxRate);
    institutionsClient.setBuy(true);
    Collections.sort(institutionsClient.getInstitutionsClient(), Collections.reverseOrder());
    return institutionsClient;
  }

  private static boolean isCurrencyTypeOther(CurrencyType currencyType) {
    if (currencyType.equals(CurrencyType.EUR) || currencyType.equals(CurrencyType.USD)
        || currencyType.equals(CurrencyType.GBP) || currencyType.equals(CurrencyType.CHF)) {
      return false;
    }
    return true;
  }

}
