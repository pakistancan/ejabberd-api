package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class SrgGetInfo implements Request {

    private final String group;
    private final String host;

    public SrgGetInfo(String host, String group) {
        this.group = group;
        this.host = host;
    }
}
