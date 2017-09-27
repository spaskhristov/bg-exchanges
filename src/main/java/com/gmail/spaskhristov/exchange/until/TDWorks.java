package com.gmail.spaskhristov.exchange.until;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.select.Elements;

import com.gmail.spaskhristov.exchange.model.Currency;
import com.gmail.spaskhristov.exchange.model.CurrencyType;

public class TDWorks {

  public static Currency work(Elements allTD, int i, int index, CurrencyType type) {
    return work(allTD, i, index, type, 1);
  }

  public static Currency work(Elements allTD, int i, int index, CurrencyType type, int divisor)
      throws NumberFormatException {
    StringBuilder buyRate = new StringBuilder();
    StringBuilder sellRate = new StringBuilder();
    Float rateBuyF = Float.parseFloat(fixedStr(allTD.get(i + index).text())) / divisor;
    Float rateSellF = Float.parseFloat(fixedStr(allTD.get(i + (index + 1)).text())) / divisor;
    buyRate.append(rateBuyF);
    sellRate.append(rateSellF);
    Currency currency = new Currency(type, buyRate.toString(), sellRate.toString());
    return currency;
  }

  public static Set<Currency> setCurrency(Elements allTD, int startIndexBuyRate,
      int startIndexSellRate, int step) {
    Set<Currency> setCurrency = new HashSet<Currency>();
    CurrencyType types[] = {CurrencyType.EUR, CurrencyType.USD, CurrencyType.GBP, CurrencyType.CHF};
    for (int i = 0; i < types.length; i++) {
      Currency currency = new Currency(types[i], fixedStr(allTD.get(startIndexBuyRate).text()),
          fixedStr(allTD.get(startIndexSellRate).text()));
      setCurrency.add(currency);
      startIndexBuyRate += step;
      startIndexSellRate += step;
    }
    return setCurrency;
  }

  public static String fixedStr(String currentStr) {
    String fixedStr = currentStr.replace(',', '.');
    if (fixedStr.endsWith(".")) {
      fixedStr = fixedStr.substring(0, fixedStr.length() - 1);
    }
    return fixedStr;
  }

}
