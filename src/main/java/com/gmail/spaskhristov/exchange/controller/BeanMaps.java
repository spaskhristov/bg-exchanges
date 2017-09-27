package com.gmail.spaskhristov.exchange.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@SessionScoped
public class BeanMaps implements Serializable {

  private static final long serialVersionUID = 1L;

  private MapModel allModel;
  private MapModel polanaModel;
  private MapModel crownModel;
  private MapModel partnerModel;
  private MapModel tavexModel;
  private MapModel varchevModel;
  private MapModel forexModel;
  private MapModel factorModel;
  private String title;

  private Marker marker;

  @PostConstruct
  public void init() {
    allModel = new DefaultMapModel();
    polanaModel = new DefaultMapModel();
    crownModel = new DefaultMapModel();
    partnerModel = new DefaultMapModel();
    tavexModel = new DefaultMapModel();
    varchevModel = new DefaultMapModel();
    forexModel = new DefaultMapModel();
    factorModel = new DefaultMapModel();
    // Shared coordinates
    LatLng coordPolana1 = new LatLng(42.69841479999999, 23.3317174);
    LatLng coordPolana2 = new LatLng(42.697, 23.3522624);
    LatLng coordCrown1 = new LatLng(42.6866436, 23.3179152);
    LatLng coordCrown2 = new LatLng(42.6985912, 23.3335663);
    LatLng coordCrown3 = new LatLng(42.7007529, 23.3228857);
    LatLng coordCrown4 = new LatLng(42.6535138, 23.342026);
    LatLng coordPartner1 = new LatLng(42.7070164, 23.3256253);
    LatLng coordPartner2 = new LatLng(42.72425399999999, 23.304523);
    LatLng coordPartner3 = new LatLng(42.7342761, 23.2945139);
    LatLng coordTavex1 = new LatLng(42.69189919999999, 23.354482);
    LatLng coordTavex2 = new LatLng(42.6852378, 23.345355);
    LatLng coordTavex3 = new LatLng(42.6982035, 23.3084974);
    LatLng coordTavex4 = new LatLng(42.6578803, 23.3146592);
    LatLng coordVarchev = new LatLng(42.68958019999999, 23.3152699);
    LatLng coordForex = new LatLng(42.6973196, 23.3353725);
    LatLng coordFactor = new LatLng(42.695144, 23.324287);
    // Basic marker
    polanaModel.addOverlay(new Marker(coordPolana1,
        "Polana 1 Change / Полана 1 (Язон)\nбул. Княз Ал. Дондуков 31 \nтел.: 029676739 , 0878181821\nработно време - денонощно - всеки ден от 00 до 24 часа",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    polanaModel.addOverlay(new Marker(coordPolana2,
        "Polana 1 Change / Полана 1 (Язон)\nбул. Мадрид 39\nтел.: 029818257\nработно време - всеки ден от 08 до 22 часа",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    crownModel.addOverlay(new Marker(coordCrown1,
        "Crown Change / Краун чейндж\nбул. Витоша 96А (НДК)\nтел.: 0892219723 и 02/4653334\nПон-Пет:08:00 ч.- 20:00 ч.\nСъбота:08:00 ч. - 20:00 ч.\nНеделя:почивен ден",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    crownModel.addOverlay(new Marker(coordCrown2,
        "Crown Change / Краун чейндж\nбул. Дондуков 44 (до НАП)\nтел.: 0893356916\nПон-Пет:08:30 ч.- 18:00 ч.\nСъбота:почивен ден\nНеделя:почивен ден",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    crownModel.addOverlay(new Marker(coordCrown3,
        "Crown Change / Краун чейндж\nбул. Княгиня Мария Луиза 20 (до Централни Хали)\nтел.: 0892219722 и 02/4654222\nПон-Пет:08:00 ч.- 18:30 ч.\nСъбота:08:30 ч. - 18:00 ч.\nНеделя:почивен ден",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    crownModel.addOverlay(new Marker(coordCrown4,
        "Crown Change / Краун чейндж\nСт.град ул. Йордан Йосифов 2 (до х-л Sweet hotel)\nтел.: 0893323532\nПон-Пет:08:00 ч.- 20:00 ч.\nСъбота:08:30 ч. - 18:00 ч.\nНеделя:почивен ден",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    partnerModel.addOverlay(new Marker(coordPartner1,
        "Change Partner / Партнер чейндж\nул. Козлодуй 61 (Дилър)\nтел./факс : 02 / 832 31 04 GSM : 0888 52 83 81\nРаботно време понеделник-събота : 9:00-18:00",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    partnerModel.addOverlay(new Marker(coordPartner2,
        "Change Partner / Партнер чейндж\nбул. Ломско шосе 53 (Пазара)\nтел./факс : 02 / 936 26 98\nРаботно време всеки ден : 8:00-20:00",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    partnerModel.addOverlay(new Marker(coordPartner3,
        "Change Partner / Партнер чейндж\nбул. Ломско шосе 172 (Данъчна служба)\nтел./факс : 02 / 936 26 48\nРаботно време понеделник-събота : 9:00-18:00",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    tavexModel.addOverlay(new Marker(coordTavex1,
        "Tavex / Тавекс\nбул. Ситняково 48, Сердика Център, ниво: -1, срещу магазин Пикадили\nтел.:02/9888666\nРаботно време Понеделник - Неделя: от 10:00 до 22:00 ч.",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    tavexModel.addOverlay(new Marker(coordTavex2,
        "Tavex / Тавекс\nбул. Цариградско Шосе 115, THE MALL - Carrefour, Ниво: -2, Срещу входа на Карфур\nтел.:02/9888666\nРаботно време Понеделник - Неделя: от 10:00 до 22:00 ч.",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    tavexModel.addOverlay(new Marker(coordTavex3,
        "Tavex / Тавекс\nбул. Ал. Стамболйски 101, Mall Of Sofia, на входа от ул. Опълченска\nтел.:02/9888666\nРаботно време Понеделник - Неделя: от 10:00 до 22:00 ч.",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    tavexModel.addOverlay(new Marker(coordTavex4,
        "Tavex / Тавекс\nбул. Черни Връх 100, Paradise Center, ниво -1, Срещу входа на Карфур\nтел.:02/9888666\nРаботно време Понеделник - Неделя: от 10:00 до 22:00 ч.",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    varchevModel.addOverlay(new Marker(coordVarchev,
        "Varchev exchange / Варчев - обменни бюра\nул. Цар Самуил 1\nтел.: 02 954 51 15; 02 953 15 51\nРаботно време: Няма въведено",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    forexModel.addOverlay(new Marker(coordForex,
        "Forex House / Форекс  чейндж\nбул. Васил Левски 100a\nтел.: 0888 544 664\nРаботно време: 24 часа в денонощието, 7 дни в седмицата",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
    factorModel.addOverlay(new Marker(coordFactor,
        "Factor I.N. / Фактор И.Н.\nул. Княз Александър 10\nтел.: 0700 17 412, 02 981 78 49, 02 981 78 50\nРаботно време: Понеделник-Петък: 08:45-18:00 Събота: 10:00-17:00",
        "", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
  }

  public MapModel getAllModel() {
    return allModel;
  }

  public void onMarkerSelect(OverlaySelectEvent event) {
    marker = (Marker) event.getOverlay();
  }

  public Marker getMarker() {
    return marker;
  }

  public String onclickSetMap(String nameEn) throws IOException {
    if (nameEn.equals("Polana 1 Change")) {
      title = "Polana 1 Change / Полана 1 (Язон)";
      setAllModelFromCurrModel(polanaModel);
    } else if (nameEn.equals("Crown Change")) {
      title = "Crown Change / Краун чейндж";
      setAllModelFromCurrModel(crownModel);
    } else if (nameEn.equals("Change Partner")) {
      title = "Change Partner / Партнер чейндж";
      setAllModelFromCurrModel(partnerModel);
    } else if (nameEn.equals("Tavex")) {
      title = "Tavex / Тавекс";
      setAllModelFromCurrModel(tavexModel);
    } else if (nameEn.equals("Varchev exchange")) {
      title = "Varchev exchange / Варчев - обменни бюра";
      setAllModelFromCurrModel(varchevModel);
    } else if (nameEn.equals("Forex House")) {
      title = "Forex House / Форекс  чейндж";
      setAllModelFromCurrModel(forexModel);
    } else if (nameEn.equals("Factor I.N.")) {
      title = "Factor I.N. / Фактор И.Н.";
      setAllModelFromCurrModel(factorModel);
    } else {
      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      nameEn = nameEn.replaceAll(" ", "+");
      externalContext
          .redirect("https://www.google.bg/maps/search/" + nameEn + "/@42.6945648,23.29968,13z");
    }
    return "maps.xhtml?faces-forward=true";
  }

  private void setAllModelFromCurrModel(MapModel currModel) {
    allModel.getMarkers().clear();
    for (Marker marker : polanaModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : crownModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : partnerModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : tavexModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : varchevModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : forexModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : factorModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/blue-dot.png");
      allModel.addOverlay(marker);
    }
    for (Marker marker : currModel.getMarkers()) {
      marker.setIcon("http://maps.google.com/mapfiles/ms/micons/red-dot.png");
    }

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
