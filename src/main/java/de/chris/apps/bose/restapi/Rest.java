package de.chris.apps.bose.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

public class Rest {

    private final static Logger LOG = LoggerFactory.getLogger(Rest.class);

    private static Rest instance = null;
    private BoseConstants constants;

    private Rest() {}

    public static Rest getInstance() {
        if (instance == null) {
            instance = new Rest();
        }
        return instance;
    }

    static Rest initInstance(BoseConstants constants) {
        getInstance().constants = constants;
        return instance;
    }

    String createEndpoint(String apiCall) {
        return constants.getRest() + apiCall;
    }

    public BoseConstants getConstants() {
        return constants;
    }

    static void checkResponseCode(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            LOG.error("Unexpected status code: " + response.statusCode());
        }
    }
}
