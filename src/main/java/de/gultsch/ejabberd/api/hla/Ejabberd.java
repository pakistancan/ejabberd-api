package de.gultsch.ejabberd.api.hla;

import de.gultsch.ejabberd.api.EjabberdApi;
import de.gultsch.ejabberd.api.RequestFailedException;
import de.gultsch.ejabberd.api.requests.CheckAccount;
import de.gultsch.ejabberd.api.requests.ConnectedUsers;
import de.gultsch.ejabberd.api.requests.Register;
import de.gultsch.ejabberd.api.requests.RegisteredVhosts;

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
        return Arrays.asList(api.execute(new ConnectedUsers(),String[].class));
    }

    public Collection<String> getRegisteredVhosts() throws RequestFailedException {
        return Arrays.asList(api.execute(new RegisteredVhosts(),String[].class));
    }

    public void register(String username, String host, String password) throws RequestFailedException {
        api.executeWithSuccessOrThrow(new Register(username,host,password));
    }

    public boolean checkAccount(String username, String host) throws RequestFailedException {
        return api.execute(new CheckAccount(username,host));
    }
}
