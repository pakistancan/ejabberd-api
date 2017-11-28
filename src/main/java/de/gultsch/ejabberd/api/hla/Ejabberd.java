package de.gultsch.ejabberd.api.hla;

import de.gultsch.ejabberd.api.EjabberdApi;
import de.gultsch.ejabberd.api.Request;
import de.gultsch.ejabberd.api.RequestFailedException;
import de.gultsch.ejabberd.api.requests.*;

import java.util.Arrays;
import java.util.Collection;

public class Ejabberd {

    private final EjabberdApi api;

    public Ejabberd() {
        this.api = new EjabberdApi();
    }

    public Ejabberd(EjabberdApi api) {
        this.api = api;
    }

    public Collection<String> getConnectedUsers() throws RequestFailedException {
        return executeAndReturnAsStringCollection(new ConnectedUsers());
    }

    private Collection<String> executeAndReturnAsStringCollection(Request request) throws RequestFailedException {
        return Arrays.asList(api.execute(request, String[].class));
    }

    public Collection<String> getRegisteredUsers(String host) throws RequestFailedException {
        return executeAndReturnAsStringCollection(new RegisteredUsers(host));
    }

    public Collection<String> getRegisteredVhosts() throws RequestFailedException {
        return executeAndReturnAsStringCollection(new RegisteredVhosts());
    }

    public void register(String username, String host, String password) throws RequestFailedException {
        api.executeWithSuccessOrThrow(new Register(username, host, password));
    }

    public boolean checkAccount(String username, String host) throws RequestFailedException {
        return api.execute(new CheckAccount(username, host));
    }

    public boolean checkPassword(String username, String host, String password) throws RequestFailedException {
        return api.execute(new CheckPassword(username,host,password));
    }

    public void changePassword(String username, String host, String newPassword) throws RequestFailedException {
        api.executeWithSuccessOrThrow(new ChangePassword(username,host,newPassword));
    }
}
