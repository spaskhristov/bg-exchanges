package com.gmail.spaskhristov.exchange.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.gmail.spaskhristov.exchange.impl.Exchange;
import com.gmail.spaskhristov.exchange.impl.InsertExchange;
import com.gmail.spaskhristov.exchange.model.CurrencyType;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.model.ExchangeModel;

@Named("beanLineChart")
@SessionScoped
public class BeanLineChart implements Serializable {

  private static final long serialVersionUID = 1L;

  private int idObject;
  private boolean isBuy = false;
  private boolean showChart = false;
  private CurrencyType currencyType;
  private LineChartModel dateModel;
  private List<ExchangeInstitution> institutions;

  @Inject
  private Exchange exchange;

  @PostConstruct
  public void init() {
    showChart = false;
    idObject = 1;
    currencyType = CurrencyType.USD;
    initInstitutions();
  }

  public LineChartModel getDateModel() {
    return dateModel;
  }

  public int getIdObject() {
    return idObject;
  }

  public void setIdObject(int idObject) {
    this.idObject = idObject;
  }

  public boolean isBuy() {
    return isBuy;
  }

  public void setBuy(boolean isBuy) {
    this.isBuy = isBuy;
  }

  public boolean isShowChart() {
    return showChart;
  }

  public void setShowChart(boolean showChart) {
    this.showChart = showChart;
  }

  public CurrencyType getCurrencyType() {
    return currencyType;
  }

  public void setCurrencyType(CurrencyType currencyType) {
    this.currencyType = currencyType;
  }

  private void initInstitutions() {
    institutions = exchange.getInstitutions();
  }

  public List<ExchangeInstitution> getInstitutions() {
    if (institutions.isEmpty()) {
      initInstitutions();
    }
    return institutions;
  }

  public void showChartAction() {
    showChart = true;
    createDateModel();
  }

  private void createDateModel() {
    dateModel = new LineChartModel();
    LineChartSeries rates = new LineChartSeries();
    InsertExchange.getInstance();
    List<ExchangeModel> listExchangeModel = InsertExchange.getExchangesModel(idObject);
    rates.setLabel(currencyType.name());
    int len = listExchangeModel.size();
    int startlen = len - 15;
    // int startlen = 0;
    for (int i = startlen; i < len; i++) {
      ExchangeModel exchangeModel = listExchangeModel.get(i);
      DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
      Date date = exchangeModel.getDate();
      String dateStr = df.format(date);
      if (isBuy) {
        if (currencyType.name().equals("USD")) {
          rates.set(dateStr, exchangeModel.getRateBuyUSD());
        } else if (currencyType.name().equals("EUR")) {
          rates.set(dateStr, exchangeModel.getRateBuyEUR());
        } else if (currencyType.name().equals("CHF")) {
          rates.set(dateStr, exchangeModel.getRateBuyCHF());
        } else if (currencyType.name().equals("GBP")) {
          rates.set(dateStr, exchangeModel.getRateBuyGBP());
        }
      } else {
        if (currencyType.name().equals("USD")) {
          rates.set(dateStr, exchangeModel.getRateSellUSD());
        } else if (currencyType.name().equals("EUR")) {
          rates.set(dateStr, exchangeModel.getRateSellEUR());
        } else if (currencyType.name().equals("CHF")) {
          rates.set(dateStr, exchangeModel.getRateSellCHF());
        } else if (currencyType.name().equals("GBP")) {
          rates.set(dateStr, exchangeModel.getRateSellGBP());
        }
      }
    }

    dateModel.addSeries(rates);

    dateModel.setTitle("Shows the last 15 days");
    dateModel.setZoom(true);
    dateModel.getAxes().put(AxisType.X, new CategoryAxis("Dates"));
    dateModel.getAxis(AxisType.Y)
        .setLabel((isBuy ? "Buy Rate " : "Sell Rate ") + currencyType.name());
    dateModel.getAxis(AxisType.Y).setMin(0);
    dateModel.getAxis(AxisType.Y).setMax(5);
    // dateModel.getAxis(AxisType.Y).setTickCount(6);
    // dateModel.getAxis(AxisType.Y).setTickInterval("5");
    // dateModel.setLegendPosition("e");
    // dateModel.getAxis(AxisType.X).setMax("16.05.2016");
    // dateModel.getAxis(AxisType.X).setMax("19.05.2016");
    dateModel.setShowPointLabels(true);
    dateModel.setAnimate(true);

  }

}
