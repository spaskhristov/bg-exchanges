package com.gmail.spaskhristov.exchange.rss;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class News implements Serializable {

  private static final long serialVersionUID = 1L;
  final static Logger logger = Logger.getLogger(News.class);
  private String header;
  private String description;
  private String imageUrl;
  private String contentUrl;
  private String pubDate;
  private String booking;

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
  }

  public String getPubDate() {
    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    Date date = new Date();
    try {
      date = format.parse(pubDate);
    } catch (ParseException e) {
      logger.error(e.getMessage(), e);
    }
    format = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
    return format.format(date);
  }

  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  public String getBooking() {
    if (imageUrl != null) {
      imageUrl = imageUrl.replace("66x50", "264x200");
      booking = contentUrl;
    } else {
      imageUrl = "http://2013.newadventuresconf.com/assets/images/content/sponsor_booking.png";
      booking = "http://www.booking.com/searchresults.html?city=-838489&aid=964020";
    }
    return booking;
  }

  public void setBooking(String booking) {
    this.booking = booking;
  }

}
