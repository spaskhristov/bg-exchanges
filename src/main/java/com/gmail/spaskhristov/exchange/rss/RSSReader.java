package com.gmail.spaskhristov.exchange.rss;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class RSSReader {

  final static Logger logger = Logger.getLogger(RSSReader.class);

  public static ArrayList<News> downloadAndParseNews(String url) {

    ArrayList<News> allNews = new ArrayList<News>();
    try {
      Document doc = Jsoup.connect(url)/* .userAgent(Config.USER_AGENT) */.ignoreContentType(true)
          .parser(Parser.xmlParser()).timeout(6000).get();
      Elements items = doc.getElementsByTag("item");
      for (int i = 0; i < items.size(); i++) {
        News news = new News();
        Element item = items.get(i);

        // Title
        Elements titles = item.getElementsByTag("title");
        if (titles != null && titles.size() > 0) {
          news.setHeader(Jsoup.clean(titles.get(0).text(), Whitelist.simpleText()));
        }

        // Link
        Elements links = item.getElementsByTag("link");
        if (links != null && links.size() > 0) {
          news.setContentUrl(links.get(0).text());
        }

        // PubDate
        Elements pubDates = item.getElementsByTag("pubDate");
        if (pubDates != null && pubDates.size() > 0) {
          news.setPubDate(pubDates.get(0).text());
        }

        // Description
        Elements descriptions = item.getElementsByTag("description");
        if (descriptions != null && descriptions.size() > 0) {
          String description = Jsoup.clean(descriptions.get(0).text(), Whitelist.simpleText());
          news.setDescription(description);
        }

        // Image
        news.setImageUrl(extractImageurl(item));

        allNews.add(news);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return allNews;
  }

  private static String extractImageurl(Element item) {
    String imageUrl = null;

    Elements enclosure = item.getElementsByTag("enclosure"); // Enclosure
    if (enclosure != null && enclosure.size() > 0) {
      imageUrl = enclosure.get(0).attr("url");
    } else {
      Elements descriptions = item.getElementsByTag("description"); // Description
      if (descriptions != null && descriptions.size() > 0) {
        Elements imgs = Jsoup.parse(descriptions.get(0).text()).getElementsByTag("img");
        if (imgs != null && imgs.size() > 0) {
          imageUrl = imgs.get(0).attr("src");
        }
      }
    }
    return imageUrl;
  }

}
