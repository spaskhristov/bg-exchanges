package com.gmail.spaskhristov.exchange.model;

import javax.ws.rs.WebApplicationException;

public enum CurrencyType {
  BGN("Лев"), EUR("Евро"), USD("Щатски долар"), CHF("Швейцарски франк"), GBP("Британска лира"), PLN(
      "Полска злота"), TRY(
          "Турска лира"), CZK("Чешка крона"), RON("Румънска лея"), RUB("Руска рубла");

  private final String fieldDescription;

  private CurrencyType(String value) {
    fieldDescription = value;
  }

  @Override
  public String toString() {
    return fieldDescription;
  }

  public static CurrencyType fromString(String param) {
    String toUpper = param.toUpperCase();
    try {
      return valueOf(toUpper);
    } catch (Exception e) {
      throw new WebApplicationException(400);
    }
  }
}
