package com.gmail.spaskhristov.exchange.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.gmail.spaskhristov.exchange.rss.News;
import com.gmail.spaskhristov.exchange.rss.RSSReader;

@Named("beanRSS")
@ApplicationScoped
public class BeanRSS implements Serializable {

  private static final long serialVersionUID = 1L;
  private List<News> messages1BG;
  private List<News> messages2BG;
  private List<News> messages3BG;
  private List<News> messages1EN;
  private List<News> messages2EN;
  private List<News> messages3EN;
  private String numVisible;

  @PostConstruct
  public void init() {
    // schedule the task to run starting now and then every hour...
    timer.schedule(hourlyTask, 0l, 1000 * 60 * 30);

  }

  public void action() {
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    int sizeWidth =
        Integer.parseInt(externalContext.getRequestParameterMap().get("form:sizeWidth"));
    if (sizeWidth <= 720) {
      numVisible = "3";
    } else if (sizeWidth <= 1200) {
      numVisible = "4";
    } else if (sizeWidth <= 1500) {
      numVisible = "5";
    } else {
      numVisible = "6";
    }
  }

  public List<News> getMessages1BG() {
    return messages1BG;
  }

  public List<News> getMessages2BG() {
    return messages2BG;
  }

  public List<News> getMessages3BG() {
    return messages3BG;
  }

  public List<News> getMessages1EN() {
    return messages1EN;
  }

  public List<News> getMessages2EN() {
    return messages2EN;
  }

  public List<News> getMessages3EN() {
    return messages3EN;
  }

  public String getNumVisible() {
    return numVisible;
  }

  public void setNumVisible(String numVisible) {
    this.numVisible = numVisible;
  }

  Timer timer = new Timer();
  TimerTask hourlyTask = new TimerTask() {

    @Override
    public void run() {
      List<News> messages1NewBG =
          RSSReader.downloadAndParseNews("http://www.bgonair.bg/rss/economy");
      List<News> messages2NewBG =
          RSSReader.downloadAndParseNews("http://www.capital.bg/rss/?rubrid=2272");
      List<News> messages3NewBG =
          RSSReader.downloadAndParseNews("http://www.dnevnik.bg/rssc/?rubrid=1666");
      List<News> messages1NewEN =
          RSSReader.downloadAndParseNews("https://finance.yahoo.com/news/rss");
      List<News> messages2NewEN =
          RSSReader.downloadAndParseNews("http://rss.cnn.com/rss/money_news_economy.rss");
      List<News> messages3NewEN =
          RSSReader.downloadAndParseNews("http://www.ft.com/rss/global-economy/eu");
      messages1BG = new ArrayList<News>();
      messages2BG = new ArrayList<News>();
      messages3BG = new ArrayList<News>();
      messages1EN = new ArrayList<News>();
      messages2EN = new ArrayList<News>();
      messages3EN = new ArrayList<News>();
      messages1BG = messages1NewBG;
      messages2BG = messages2NewBG;
      messages3BG = messages3NewBG;
      messages1EN = messages1NewEN;
      messages2EN = messages2NewEN;
      messages3EN = messages3NewEN;
    }
  };

}
