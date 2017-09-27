package com.gmail.spaskhristov.exchange.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

import com.gmail.spaskhristov.exchange.dto.ExchangeInstitutionsDTO;
import com.gmail.spaskhristov.exchange.impl.Exchange;
import com.gmail.spaskhristov.exchange.model.BNBfixingSet;
import com.gmail.spaskhristov.exchange.model.CurrencyType;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.utils.CurrencyCalc;

@Named("beanExchange")
@SessionScoped
public class BeanExchange implements Serializable {

  private static final long serialVersionUID = 1L;
  final static Logger logger = Logger.getLogger(BeanExchange.class);

  @Inject
  private Exchange exchange;

  private CurrencyType fromCurrType;
  private CurrencyType toCurrType;
  private CurrencyType buyCurrType;
  private float rate = 1f;
  private float rateBuy = 1f;
  private ExchangeInstitutionsDTO institutionsClient;
  private List<CurrencyType> allCurrencyTypeTo;
  private List<CurrencyType> currencyTypeToBGN;
  private List<CurrencyType> currencyTypeToWithoutBGN;
  private String position;

  public List<ExchangeInstitution> getInstitutions() {
    return exchange.getInstitutions();
  }

  public List<ExchangeInstitution> getInstitutionsOther() {
    return exchange.getInstitutionsOther();
  }

  public BNBfixingSet getFixingBNBOther() {
    return exchange.getFixingBNBSetOther();
  }

  public BNBfixingSet getFixingBNB() {
    return exchange.getFixingBNBSet();
  }

  public List<CurrencyType> getAllCurrencyTypeBuy() {
    List<CurrencyType> allCurrencyTypeBuy = new ArrayList<CurrencyType>();
    for (CurrencyType currencyType : CurrencyType.values()) {
      if (!currencyType.name().equals("BGN")) {
        allCurrencyTypeBuy.add(currencyType);
      }
    }
    return allCurrencyTypeBuy;
  }

  public List<CurrencyType> getAllCurrencyTypeFrom() {
    List<CurrencyType> allCurrencyTypeFrom = new ArrayList<CurrencyType>();
    currencyTypeToBGN = new ArrayList<CurrencyType>();
    currencyTypeToWithoutBGN = new ArrayList<CurrencyType>();
    for (CurrencyType currencyType : CurrencyType.values()) {
      allCurrencyTypeFrom.add(currencyType);
      if (currencyType.name().equals("BGN")) {
        currencyTypeToBGN.add(currencyType);
      } else {
        currencyTypeToWithoutBGN.add(currencyType);
      }
    }
    return allCurrencyTypeFrom;
  }

  public void onCurrencyTypeFromChange() {
    if (fromCurrType == null || fromCurrType.name().equals("BGN")) {
      this.allCurrencyTypeTo = currencyTypeToWithoutBGN;
    } else {
      this.allCurrencyTypeTo = currencyTypeToBGN;
    }
  }

  public List<CurrencyType> getAllCurrencyTypeTo() {
    onCurrencyTypeFromChange();
    return allCurrencyTypeTo;
  }

  public String getLastRefresh() {
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
    format.setTimeZone(TimeZone.getTimeZone("EET"));
    return format.format(exchange.getLastRefresh());
  }

  public void calcCurrency() {
    setInstitutionsClient(CurrencyCalc.calcCurrency(exchange, fromCurrType, toCurrType, rate));
  }

  public void calcCurrencyBuy() {
    setInstitutionsClient(CurrencyCalc.calcCurrencyBuy(exchange, buyCurrType, rateBuy));
  }

  public String getSubResult(String currRate) {
    if (currRate != null) {
      try {
        Float fcurrRate = Float.parseFloat(currRate);
        Float fminRate = Float.parseFloat(institutionsClient.getMinRate());
        Float fmaxRate = Float.parseFloat(institutionsClient.getMaxRate());
        if (institutionsClient.isBuy()) {
          return String.format("%.5f", (fmaxRate - fcurrRate));
        }
        return String.format("%.5f", (fcurrRate - fminRate));
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        return "";
      }
    }
    return "";
  }

  public boolean equalsRateRed(String currRate) {
    if (currRate != null) {
      try {
        Float.parseFloat(currRate);
        if (institutionsClient.isBuy()) {
          return currRate.equals(institutionsClient.getMaxRate());
        }
        return currRate.equals(institutionsClient.getMinRate());
      } catch (Exception e) {
        return false;
      }
    }
    return false;
  }

  public boolean equalsRateGreen(String currRate) {
    if (currRate != null) {
      try {
        Float.parseFloat(currRate);
        if (institutionsClient.isBuy()) {
          return currRate.equals(institutionsClient.getMinRate());
        }
        return currRate.equals(institutionsClient.getMaxRate());
      } catch (Exception e) {
        return false;
      }
    }
    return false;
  }

  public void postProcessXLS(Object document) {
    String headerText = "Последна актуализация " + this.getLastRefresh() + "\nВалутни курсове";
    HSSFWorkbook wb = (HSSFWorkbook) document;
    HSSFSheet sheet = wb.getSheetAt(0);
    sheet.getPrintSetup().setLandscape(true);
    sheet.getHeader().setCenter(headerText);
    HSSFCellStyle headerStyle = wb.createCellStyle();
    headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
    headerStyle.setWrapText(true);
    HSSFRow headerRow = sheet.getRow(0);
    for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
      HSSFCell cell = headerRow.getCell(i);
      cell.setCellStyle(headerStyle);
    }
  }

  public void preProcessXLS(Object document) {
    HSSFWorkbook workbook = (HSSFWorkbook) document;
    HSSFSheet sheet = workbook.getSheetAt(0);
    DataFormat format = workbook.createDataFormat();
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(format.getFormat("@"));
    sheet.setDefaultColumnStyle(0, cellStyle);
    sheet.setDefaultColumnStyle(1, cellStyle);
  }

  public CurrencyType getBuyCurrType() {
    return buyCurrType;
  }

  public void setBuyCurrType(CurrencyType buyCurrType) {
    this.buyCurrType = buyCurrType;
  }

  public CurrencyType getFromCurrType() {
    return fromCurrType;
  }

  public void setFromCurrType(CurrencyType fromCurrType) {
    this.fromCurrType = fromCurrType;
  }

  public CurrencyType getToCurrType() {
    return toCurrType;
  }

  public void setToCurrType(CurrencyType toCurrType) {
    this.toCurrType = toCurrType;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public float getRateBuy() {
    return rateBuy;
  }

  public void setRateBuy(float rateBuy) {
    this.rateBuy = rateBuy;
  }

  public ExchangeInstitutionsDTO getInstitutionsClient() {
    return institutionsClient;
  }

  public void setInstitutionsClient(ExchangeInstitutionsDTO institutionsClient) {
    this.institutionsClient = institutionsClient;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

}
