package de.chris.apps.bose.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:bose.properties")
public class BoseConstants {

    private final String protocol;
    private final String spotifyAccount;
    private final String rest;
    private final String websocket;

    @Autowired
    BoseConstants(Environment env) {
        rest = env.getProperty("bose.rest");
        websocket = env.getProperty("bose.websocket");
        protocol=env.getProperty("bose.protocol");
        spotifyAccount=env.getProperty("spotify.account");
        Rest.initInstance(this);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSpotifiyAccount() {
        return spotifyAccount;
    }

    public String getRest() {
        return rest;
    }

    public String getWebsocket() {
        return websocket;
    }
}
