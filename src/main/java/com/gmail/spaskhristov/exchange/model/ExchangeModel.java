package com.gmail.spaskhristov.exchange.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_table")
@NamedQueries({@NamedQuery(name = "ExchangeModel.findRegByIDObject",
    query = "SELECT exch FROM ExchangeModel exch WHERE exch.idObject=:idObject")})
public class ExchangeModel implements Serializable, Comparable<ExchangeModel> {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", length = 1000)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "idObject", length = 1000)
  private int idObject;

  @Column(name = "date", length = 10)
  private Date date;

  @Column(name = "rateBuyUSD", length = 1000)
  private float rateBuyUSD;

  @Column(name = "rateSellUSD", length = 1000)
  private float rateSellUSD;

  @Column(name = "rateBuyEUR", length = 1000)
  private float rateBuyEUR;

  @Column(name = "rateSellEUR", length = 1000)
  private float rateSellEUR;

  @Column(name = "rateBuyCHF", length = 1000)
  private float rateBuyCHF;

  @Column(name = "rateSellCHF", length = 1000)
  private float rateSellCHF;

  @Column(name = "rateBuyGBP", length = 1000)
  private float rateBuyGBP;

  @Column(name = "rateSellGBP", length = 1000)
  private float rateSellGBP;

  public ExchangeModel() {}

  public ExchangeModel(int idObj, float rateBuyUSD, float rateSellUSD, float rateBuyEUR,
      float rateSellEUR, float rateBuyCHF, float rateSellCHF, float rateBuyGBP, float rateSellGBP) {
    this.setIdObject(idObj);
    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();
    this.setDate(today);
    this.setRateBuyUSD(rateBuyUSD);
    this.setRateSellUSD(rateSellUSD);
    this.setRateBuyEUR(rateBuyEUR);
    this.setRateSellEUR(rateSellEUR);
    this.setRateBuyCHF(rateBuyCHF);
    this.setRateSellCHF(rateSellCHF);
    this.setRateBuyGBP(rateBuyGBP);
    this.setRateSellGBP(rateSellGBP);
  }

  public int getIdObject() {
    return idObject;
  }

  public void setIdObject(int idObject) {
    this.idObject = idObject;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public float getRateBuyUSD() {
    return rateBuyUSD;
  }

  public void setRateBuyUSD(float rateBuyUSD) {
    this.rateBuyUSD = rateBuyUSD;
  }

  public float getRateSellUSD() {
    return rateSellUSD;
  }

  public void setRateSellUSD(float rateSellUSD) {
    this.rateSellUSD = rateSellUSD;
  }

  public float getRateBuyEUR() {
    return rateBuyEUR;
  }

  public void setRateBuyEUR(float rateBuyEUR) {
    this.rateBuyEUR = rateBuyEUR;
  }

  public float getRateSellEUR() {
    return rateSellEUR;
  }

  public void setRateSellEUR(float rateSellEUR) {
    this.rateSellEUR = rateSellEUR;
  }

  public float getRateBuyCHF() {
    return rateBuyCHF;
  }

  public void setRateBuyCHF(float rateBuyCHF) {
    this.rateBuyCHF = rateBuyCHF;
  }

  public float getRateSellCHF() {
    return rateSellCHF;
  }

  public void setRateSellCHF(float rateSellCHF) {
    this.rateSellCHF = rateSellCHF;
  }

  public float getRateBuyGBP() {
    return rateBuyGBP;
  }

  public void setRateBuyGBP(float rateBuyGBP) {
    this.rateBuyGBP = rateBuyGBP;
  }

  public float getRateSellGBP() {
    return rateSellGBP;
  }

  public void setRateSellGBP(float rateSellGBP) {
    this.rateSellGBP = rateSellGBP;
  }

  @Override
  public int compareTo(ExchangeModel exchangeModel) {
    if (this.date.after(exchangeModel.date)) {
      return 1;
    } else if (this.date.before(exchangeModel.date)) {
      return -1;
    }
    return 0;
  }

}
