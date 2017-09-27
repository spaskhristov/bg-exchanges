package com.gmail.spaskhristov.exchange.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.gmail.spaskhristov.exchange.model.ExchangeModel;

public class InsertExchange {

  private static EntityManager em;

  private static InsertExchange instance = null;

  protected InsertExchange() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExchangeService");
    em = emf.createEntityManager();
  }

  public static InsertExchange getInstance() {
    if (instance == null) {
      instance = new InsertExchange();
    }
    return instance;
  }

  public static void createExchangeEntity(int idObject, float rateBuyUSD, float rateSellUSD,
      float rateBuyEUR, float rateSellEUR, float rateBuyCHF, float rateSellCHF, float rateBuyGBP,
      float rateSellGBP) {
    em.getTransaction().begin();
    ExchangeModel entity = new ExchangeModel(idObject, rateBuyUSD, rateSellUSD, rateBuyEUR,
        rateSellEUR, rateBuyCHF, rateSellCHF, rateBuyGBP, rateSellGBP);
    em.persist(entity);
    em.getTransaction().commit();
  }

  public static List<ExchangeModel> getExchangesModel(int idObject) {
    List<ExchangeModel> result =
        em.createNamedQuery("ExchangeModel.findRegByIDObject", ExchangeModel.class)
            .setParameter("idObject", idObject).getResultList();
    Collections.sort(result);
    return result;
  }

}
