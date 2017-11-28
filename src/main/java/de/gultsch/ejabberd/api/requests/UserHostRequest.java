package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public abstract class UserHostRequest implements Request {

    private final String user;
    private final String host;

    protected UserHostRequest(String user, String host) {
        this.user = user;
        this.host = host;
    }
}
