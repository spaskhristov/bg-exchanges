package com.gmail.spaskhristov.exchange.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "language")
@SessionScoped
public class BeanLanguage implements Serializable {

  private static final long serialVersionUID = 1L;

  private boolean isLocaleBG = true;

  public void countryLocaleBG() {
    isLocaleBG = true;
  }

  public void countryLocaleEN() {
    isLocaleBG = false;
  }

  public Locale getLocale() {
    if (isLocaleBG) {
      return Locale.forLanguageTag("bg");
    }
    return Locale.ENGLISH;
  }

  public boolean isLocaleBG() {
    return isLocaleBG;
  }

}
