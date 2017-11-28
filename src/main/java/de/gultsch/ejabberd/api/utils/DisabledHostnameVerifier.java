package de.gultsch.ejabberd.api.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class DisabledHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
