package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class CheckAccount implements Request {
    public final String user;
    public final String host;

    public CheckAccount(String user, String host) {
        this.user = user;
        this.host = host;
    }
}
