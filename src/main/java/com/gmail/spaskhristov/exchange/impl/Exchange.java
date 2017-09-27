package com.gmail.spaskhristov.exchange.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.gmail.spaskhristov.exchange.model.BNBfixingSet;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.until.ExcelRead;
import com.gmail.spaskhristov.exchange.until.HTMLRead;

@Named("exchange")
@ApplicationScoped
public class Exchange {

  private List<ExchangeInstitution> institutions = ExcelRead.getInstitutions();
  private BNBfixingSet fixingBNBSet = new BNBfixingSet();
  private List<ExchangeInstitution> institutionsOther = ExcelRead.getInstitutionsOther();
  private BNBfixingSet fixingBNBSetOther = new BNBfixingSet();
  private Date lastRefresh = new Date();
  private Timer timer = new Timer();
  private TimerTask hourlyTask = new TimerTask() {

    @Override
    public void run() {
      List<ExchangeInstitution> institutionsNew =
          HTMLRead.fillInstitutions(ExcelRead.getInstitutions(), true);
      institutionsNew = prepareInstitutions(getInstitutions(), institutionsNew);
      BNBfixingSet fixingBNBSetNew = HTMLRead.getFixingBNBSet();

      List<ExchangeInstitution> institutionsOtherNew =
          HTMLRead.fillInstitutions(ExcelRead.getInstitutionsOther(), false);
      institutionsOtherNew = prepareInstitutions(getInstitutionsOther(), institutionsOtherNew);
      BNBfixingSet fixingBNBSetOtherNew = HTMLRead.getFixingBNBSetOther();

      lastRefresh = new Date();
      institutions = institutionsNew;
      institutionsOther = institutionsOtherNew;
      fixingBNBSet = fixingBNBSetNew;
      fixingBNBSetOther = fixingBNBSetOtherNew;
    }
  };

  @PostConstruct
  public void init() {
    // schedule the task to run starting now and then every hour...
    timer.schedule(hourlyTask, 0l, 1000 * 60 * 60 * 2);
  }

  public List<ExchangeInstitution> getInstitutions() {
    if (institutions == null) {
      fillAllInstitutions();
    }
    return institutions;
  }

  private void fillAllInstitutions() {
    institutions = ExcelRead.getInstitutions();
    institutions = HTMLRead.fillInstitutions(institutions, true);
    institutionsOther = ExcelRead.getInstitutionsOther();
    institutionsOther = HTMLRead.fillInstitutions(institutionsOther, false);
  }

  public List<ExchangeInstitution> getInstitutionsOther() {
    if (institutionsOther == null) {
      fillAllInstitutions();
    }
    return institutionsOther;
  }

  public BNBfixingSet getFixingBNBSet() {
    return fixingBNBSet;
  }

  public BNBfixingSet getFixingBNBSetOther() {
    return fixingBNBSetOther;
  }

  public Date getLastRefresh() {
    return lastRefresh;
  }

  private List<ExchangeInstitution> prepareInstitutions(List<ExchangeInstitution> institutions,
      List<ExchangeInstitution> institutionsNew) {
    /*
     * if (institutions.size() > institutionsNew.size()) { for (ExchangeInstitution inst :
     * institutions) { if (!institutionsNew.contains(inst)) { institutionsNew.add(inst); } } }
     */
    Iterator<ExchangeInstitution> iterator = institutionsNew.iterator();
    while (iterator.hasNext()) {
      ExchangeInstitution instNew = iterator.next();
      if (instNew.getSetCurrency().isEmpty()) {
        iterator.remove();
      }
    }
    return institutionsNew;
  }

}
