package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class SrgDelete implements Request {

    private final String host;
    private final String group;

    public SrgDelete(String host, String group) {
        this.host = host;
        this.group = group;
    }
}
