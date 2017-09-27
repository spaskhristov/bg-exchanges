package com.gmail.spaskhristov.exchange.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Create a class to trust all hosts, so always returns true
 */

public class HostnameVerifierImpl implements HostnameVerifier {
  public boolean verify(String urlHostname, SSLSession sslSession) {
    return true;
  }
}
