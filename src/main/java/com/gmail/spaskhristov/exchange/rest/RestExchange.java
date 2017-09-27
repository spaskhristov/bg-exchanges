package com.gmail.spaskhristov.exchange.rest;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gmail.spaskhristov.exchange.dto.ExchangeInstitutionsDTO;
import com.gmail.spaskhristov.exchange.impl.Exchange;
import com.gmail.spaskhristov.exchange.impl.InsertExchange;
import com.gmail.spaskhristov.exchange.model.Currency;
import com.gmail.spaskhristov.exchange.model.CurrencyType;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.utils.CurrencyCalc;

@Path("/")
@ApplicationScoped
public class RestExchange implements Serializable {

  @Inject
  Exchange exchange;

  private static final long serialVersionUID = 1L;
  private Timer timer = new Timer();
  private TimerTask hourlyTask = new TimerTask() {

    @Override
    public void run() {
      for (ExchangeInstitution object : exchange.getInstitutions()) {
        int idObject = object.getId();
        float rateBuyUSD = 0;
        float rateSellUSD = 0;
        float rateBuyEUR = 0;
        float rateSellEUR = 0;
        float rateBuyCHF = 0;
        float rateSellCHF = 0;
        float rateBuyGBP = 0;
        float rateSellGBP = 0;
        for (Currency objectCurrency : object.getSetCurrency()) {
          int ordinal = objectCurrency.getName().ordinal();
          switch (ordinal) {
            case 1:
              rateBuyUSD = Float.parseFloat(objectCurrency.getRateBuy());
              rateSellUSD = Float.parseFloat(objectCurrency.getRateSell());
              break;
            case 2:
              rateBuyEUR = Float.parseFloat(objectCurrency.getRateBuy());
              rateSellEUR = Float.parseFloat(objectCurrency.getRateSell());
              break;
            case 3:
              rateBuyCHF = Float.parseFloat(objectCurrency.getRateBuy());
              rateSellCHF = Float.parseFloat(objectCurrency.getRateSell());
              break;
            case 4:
              rateBuyGBP = Float.parseFloat(objectCurrency.getRateBuy());
              rateSellGBP = Float.parseFloat(objectCurrency.getRateSell());
              break;
            default:
              break;
          }
        }
        InsertExchange.createExchangeEntity(idObject, rateBuyUSD, rateSellUSD, rateBuyEUR,
            rateSellEUR, rateBuyCHF, rateSellCHF, rateBuyGBP, rateSellGBP);
      }
    }
  };

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public Response getAllBanks() {
    return Response.ok(exchange.getInstitutions()).build();
  }

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public Response getCurrentBank(@PathParam("id") String id) throws URISyntaxException {
    if (id.matches("[0-9]{0,3}")) {
      for (ExchangeInstitution institution : exchange.getInstitutions()) {
        if (institution.getId() == Integer.valueOf(id).intValue()) {
          return Response.ok(institution).build();
        }
      }
    } else if (id.equals("startStatistic")) {
      InsertExchange.getInstance();
      timer.schedule(hourlyTask, 0l, 1000 * 60 * 60 * 24);
      return Response.ok("Start").build();
    }
    return Response.seeOther(new URI("/../error.xhtml")).build();
  }

  @GET
  @Path("calc")
  /*
   * http://localhost:8080/exchange/rest/calc?fromCurrType=usd&toCurrType=bgn&rate=1
   */
  public ExchangeInstitutionsDTO getCalcInstitutions(
      @QueryParam("fromCurrType") CurrencyType fromCurrType,
      @QueryParam("toCurrType") CurrencyType toCurrType, @QueryParam("rate") float rate) {
    ExchangeInstitutionsDTO institutionsClient =
        CurrencyCalc.calcCurrency(exchange, fromCurrType, toCurrType, rate);
    return institutionsClient;
  }

  @GET
  @Path("calcBuy")
  /*
   * http://localhost:8080/exchange/rest/calcBuy?currType=usd&rate=1
   */
  public ExchangeInstitutionsDTO getCalcBuyInstitutions(
      @QueryParam("currType") CurrencyType currType, @QueryParam("rate") float rate) {
    ExchangeInstitutionsDTO institutionsClient =
        CurrencyCalc.calcCurrencyBuy(exchange, currType, rate);
    return institutionsClient;
  }

}
