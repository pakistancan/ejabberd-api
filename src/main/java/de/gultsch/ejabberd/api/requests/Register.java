package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class Register implements Request {
    private final String user;
    private final String host;
    private final String password;

    public Register(String username, String host, String password) {
        this.user = username;
        this.host = host;
        this.password = password;
    }
}
