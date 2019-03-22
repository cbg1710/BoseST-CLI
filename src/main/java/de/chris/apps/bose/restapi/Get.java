package de.chris.apps.bose.restapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Get {

    private final HttpClient client;
    private final HttpRequest request;

    private Get(String apiCall) {
        client = HttpClient.newBuilder().build();
        request = HttpRequest.newBuilder()
                .uri(URI.create(Rest.getInstance().createEndpoint(apiCall)))
                .build();
    }

    public static String get(String apiCall) {
        try {
            Get get = new Get(apiCall);
            return get.tryGet();
        }
        catch (IOException | InterruptedException e) {
            throw new GetResponseFailure(apiCall);
        }
    }

    private String tryGet() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Rest.checkResponseCode(response);
        return response.body();
    }

    static class GetResponseFailure extends RuntimeException {
        GetResponseFailure(String message) {
            super(String.format("Failure during call GET %s", message));
        }
    }
}
