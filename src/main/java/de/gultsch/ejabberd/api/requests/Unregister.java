package de.gultsch.ejabberd.api.requests;

public class Unregister extends UserHostRequest {
    public Unregister(String user, String host) {
        super(user, host);
    }
}
