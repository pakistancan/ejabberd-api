package de.gultsch.ejabberd.api.requests;

import de.gultsch.ejabberd.api.Request;

public class SrgUserAdd implements Request {
    private final String user;
    private final String host;
    private final String group;
    private final String grouphost;

    public SrgUserAdd(String host, String group, String user, String grouphost) {
        this.user = user;
        this.host = host;
        this.group = group;
        this.grouphost = grouphost;
    }
}
