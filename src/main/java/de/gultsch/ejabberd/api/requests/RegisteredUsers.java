package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class RegisteredUsers implements Request {
    private final String host;

    public RegisteredUsers(String host) {
        this.host = host;
    }
}
