package com.gmail.spaskhristov.exchange.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.gmail.spaskhristov.exchange.until.Constants;

/**
 * Create a trust manager that does not validate certificate chains
 */

public class TrustManagerImpl implements TrustManager, X509TrustManager {
  @Override
  public void checkClientTrusted(X509Certificate[] certs, String authType)
      throws CertificateException {
    return;
  }

  @Override
  public void checkServerTrusted(X509Certificate[] certs, String authType)
      throws CertificateException {
    return;
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }

  public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
    return true;
  }

  public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
    return true;
  }

  public static synchronized void trustAllHttpsCertificates() throws Exception {
    javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
    javax.net.ssl.TrustManager tm = new TrustManagerImpl();
    trustAllCerts[0] = tm;
    javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance(Constants.SSL);
    sc.init(null, trustAllCerts, null);
    javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }

}
