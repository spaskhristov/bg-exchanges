package com.gmail.spaskhristov.exchange.until;

import com.gmail.spaskhristov.exchange.model.BNBfixing;
import com.gmail.spaskhristov.exchange.model.BNBfixingSet;
import com.gmail.spaskhristov.exchange.model.Currency;
import com.gmail.spaskhristov.exchange.model.CurrencyType;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.ssl.HostnameVerifierImpl;
import com.gmail.spaskhristov.exchange.ssl.TrustManagerImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLRead {

    private static BNBfixingSet fixingBNBSet;
    private static BNBfixingSet fixingBNBSetOther;
    final static Logger logger = Logger.getLogger(HTMLRead.class);

    private HTMLRead() {
    }

    public static BNBfixingSet getFixingBNBSet() {
        checkFixingIsEmpty();
        return fixingBNBSet;
    }

    public static BNBfixingSet getFixingBNBSetOther() {
        checkFixingIsEmpty();
        return fixingBNBSetOther;
    }

    private static void checkFixingIsEmpty() {
        if (fixingBNBSet == null || fixingBNBSet.getFixingBNB().isEmpty()) {
            try {
                fillFixingBNB();
            } catch (MalformedURLException e) {
                logger.error(e.getMessage(), e);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static List<ExchangeInstitution> fillInstitutions(List<ExchangeInstitution> institutions,
            boolean isPopularCurrency) {
        // Install the all-trusting trust manager
        try {
            TrustManagerImpl.trustAllHttpsCertificates();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifierImpl());

        if (isPopularCurrency) {
            for (ExchangeInstitution institution : institutions) {
                try {
                    Set<Currency> setCurrency = new HashSet<Currency>();
                    URL institutionURL = new URL(institution.getUrlStr());
                    Document doc = Jsoup.parse(institutionURL, 60000);
                    Elements allTD = doc.select("td");

                    if (institution.getId() == 9) {
                        institution.setSetCurrency(TDWorks.setCurrency(allTD, 4, 1, 9));
                        continue;
                    } else if (institution.getId() == 16) {
                        allTD = doc.select("dd");
                        institution.setSetCurrency(TDWorks.setCurrency(allTD, 0, 1, 3));
                        continue;
                    } else if (institution.getId() == 19) {
                        allTD = doc.getElementsByTag("span");
                    } else if (institution.getId() == 10) {
                        allTD = doc.select("span.js-price-widget-sell, span.js-price-widget-buy, abbr.table-flex__abbr");
                    }
                    int sizeAllTD = allTD.size() - 2;
                    for (int i = 0; i < sizeAllTD; i++) {
                        Element td = allTD.get(i);
                        String strTd = td.text().trim().toLowerCase();
                        if (checkStartWithCurrency(strTd, true)) {
                            CurrencyType currencyType = getCurrencyType(strTd, institution.getId(), true);
                            Currency currency = null;
                            switch (institution.getId()) {
                                case 5:
                                case 7:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 21:
                                    try {
                                        currency = TDWorks.work(allTD, i, 1, currencyType);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    break;
                                case 1:
                                case 17:
                                case 18:
                                case 19:
                                case 20:
                                    try {
                                        currency = TDWorks.work(allTD, i, 2, currencyType);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    break;
                                case 2:
                                case 3:
                                    try {
                                        currency = TDWorks.work(allTD, i, 3, currencyType);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    break;
                                case 22:
                                    try {
                                        currency = TDWorks.work(allTD, i, 4, currencyType);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    break;
                                case 6:
                                    try {
                                        currency = TDWorks.work(allTD, i, 6, currencyType);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            setCurrency.add(currency);
                        }
                    }
                    institution.setSetCurrency(setCurrency);
                } catch (Exception e) {
                    logger.error(institution.getName(), e);
                }
            }

            return institutions;
        }

        for (ExchangeInstitution institution : institutions) {
            try {
                Set<Currency> setCurrency = new HashSet<Currency>();
                URL institutionURL = new URL(institution.getUrlStr());
                System.setProperty("jsse.enableSNIExtension", "false");
                Document doc = null;
                doc = Jsoup.parse(institutionURL, 60000);
                Elements allTD = doc.select("td");

                if (institution.getId() == 19) {
                    allTD = doc.getElementsByTag("span");
                } else if (institution.getId() == 10) {
                    allTD = doc.select("span.js-price-widget-sell, span.js-price-widget-buy, abbr.table-flex__abbr");
                }

                int sizeAllTD = allTD.size();
                for (int i = 0; i < sizeAllTD; i++) {
                    Element td = allTD.get(i);
                    String strTd = td.text().trim().toLowerCase();
                    if (checkStartWithCurrency(strTd, false)) {
                        CurrencyType currencyType = getCurrencyType(strTd, institution.getId(), false);
                        Currency currency = null;
                        switch (institution.getId()) {
                            case 12:
                                try {
                                    if (currencyType == CurrencyType.PLN || currencyType == CurrencyType.CZK) {
                                        currency = TDWorks.work(allTD, i, 1, currencyType, 100);
                                    } else if (currencyType == CurrencyType.RUB) {
                                        currency = TDWorks.work(allTD, i, 1, currencyType, 1000);
                                    } else {
                                        currency = TDWorks.work(allTD, i, 1, currencyType);
                                    }
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 10:
                            case 11:
                            case 13:
                            case 15:
                                try {
                                    currency = TDWorks.work(allTD, i, 1, currencyType);
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 1:
                                try {
                                    if (currencyType == CurrencyType.CZK || currencyType == CurrencyType.RUB) {
                                        currency = TDWorks.work(allTD, i, 2, currencyType, 100);
                                    } else {
                                        currency = TDWorks.work(allTD, i, 2, currencyType, 10);
                                    }
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 19:
                                try {
                                    currency = TDWorks.work(allTD, i, 2, currencyType);
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 3:
                                try {
                                    if (currencyType == CurrencyType.CZK || currencyType == CurrencyType.RUB) {
                                        currency = TDWorks.work(allTD, i, 3, currencyType, 100);
                                    } else {
                                        currency = TDWorks.work(allTD, i, 3, currencyType, 10);
                                    }
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 2:
                                try {
                                    if (currencyType == CurrencyType.RON && strTd.startsWith("ron")) {
                                        currency = TDWorks.work(allTD, i, 3, currencyType, 10);
                                    } else if (currencyType == CurrencyType.RUB && strTd.startsWith("rub")) {
                                        currency = TDWorks.work(allTD, i, 3, currencyType, 100);
                                    } else {
                                        continue;
                                    }

                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            case 22:
                                try {
                                    currency = TDWorks.work(allTD, i, 3, currencyType);
                                } catch (NumberFormatException e) {
                                    continue;
                                }
                                break;
                            default:
                                break;
                        }
                        setCurrency.add(currency);
                    }
                }
                institution.addSetCurrency(setCurrency);
            } catch (Exception e) {
                logger.error(institution.getName(), e);
            }
        }

        return institutions;

    }

    private static void fillFixingBNB() throws IOException {
        Set<BNBfixing> SetOfFixingBNB = new HashSet<BNBfixing>();
        Set<BNBfixing> SetOtherOfFixingBNB = new HashSet<BNBfixing>();
        URL urlBNB = new URL(Constants.URL_BNB);
        Document doc = Jsoup.parse(urlBNB, 60000);
        Elements allTD = doc.select("td");
        for (int index = 0; index < allTD.size(); index++) {
            Element td = allTD.get(index);
            String strTd = td.text().trim().toLowerCase();
            if (strTd.startsWith("usd") || strTd.startsWith("chf") || strTd.startsWith("gbp")) {
                StringBuilder rateReference = new StringBuilder();
                CurrencyType currencyType = CurrencyType.GBP;
                if (strTd.startsWith("usd")) {
                    currencyType = CurrencyType.USD;
                } else if (strTd.startsWith("chf")) {
                    currencyType = CurrencyType.CHF;
                }
                rateReference.append(TDWorks.fixedStr(allTD.get(index + 2).text()));
                BNBfixing fixingBNB = new BNBfixing(currencyType, rateReference.toString());
                SetOfFixingBNB.add(fixingBNB);
            } else if (strTd.startsWith("pln") || strTd.startsWith("try") || strTd.startsWith("czk")
                    || strTd.startsWith("ron") || strTd.startsWith("rub")) {
                StringBuilder rateReference = new StringBuilder();
                CurrencyType currencyType = CurrencyType.PLN;
                if (strTd.startsWith("try")) {
                    currencyType = CurrencyType.TRY;
                } else if (strTd.startsWith("czk")) {
                    currencyType = CurrencyType.CZK;
                } else if (strTd.startsWith("ron")) {
                    currencyType = CurrencyType.RON;
                } else if (strTd.startsWith("rub")) {
                    currencyType = CurrencyType.RUB;
                }
                rateReference.append(TDWorks.fixedStr(allTD.get(index + 2).text()));
                BNBfixing fixingBNB = new BNBfixing(currencyType, rateReference.toString());
                SetOtherOfFixingBNB.add(fixingBNB);
            }

        }
        SetOfFixingBNB.add(new BNBfixing(CurrencyType.EUR, "1.95583"));
        fixingBNBSet = new BNBfixingSet(SetOfFixingBNB);
        fixingBNBSetOther = new BNBfixingSet(SetOtherOfFixingBNB);
    }

    private static CurrencyType getCurrencyType(String strTd, int institutionID,
            boolean isPopularCurrency) {
        CurrencyType currencyType = CurrencyType.RON;
        if (isPopularCurrency) {
            currencyType = CurrencyType.GBP;
        }
        if (isContainsInArray(Constants.WORDS_USD, strTd)) {
            currencyType = CurrencyType.USD;
        } else if (isContainsInArray(Constants.WORDS_EUR, strTd)) {
            currencyType = CurrencyType.EUR;
        } else if (isContainsInArray(Constants.WORDS_CHF, strTd)) {
            currencyType = CurrencyType.CHF;
        } else if (isContainsInArray(Constants.WORDS_PLN, strTd)) {
            currencyType = CurrencyType.PLN;
        } else if (isContainsInArray(Constants.WORDS_TRY, strTd)) {
            currencyType = CurrencyType.TRY;
        } else if (isContainsInArray(Constants.WORDS_CZK, strTd)) {
            currencyType = CurrencyType.CZK;
        } else if (isContainsInArray(Constants.WORDS_RUB, strTd)
                || (strTd.startsWith("руска") && institutionID == 14)) {
            currencyType = CurrencyType.RUB;
        }
        return currencyType;
    }

    private static boolean checkStartWithCurrency(String strTd, boolean isPopularCurrency) {
        if (isPopularCurrency) {
            return isContainsInArray(Constants.WORDS_ALL_POP, strTd);
        }
        return isContainsInArray(Constants.WORDS_ALL_OTHER, strTd);
    }

    private static boolean isContainsInArray(String[] arrayStr, String word) {
        for (String str : arrayStr) {
            if (word.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

}
