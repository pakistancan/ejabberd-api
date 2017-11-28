package de.gultsch.ejabberd.api.requests;

public class CheckPassword extends UserHostRequest {

    private final String password;

    public CheckPassword(String user, String host, String password) {
        super(user, host);
        this.password = password;
    }
}
