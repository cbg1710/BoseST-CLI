package de.chris.apps.bose.restapi;

import de.chris.apps.bose.xml.util.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Post<T> {
    private final static Logger LOG = LoggerFactory.getLogger(Post.class);

    private final String apiCall;

    private HttpClient client;
    private HttpRequest request;
    private Class<?> clazz;
    private Object o;

    Post(String apiCall) {
        this.apiCall = apiCall;
    }

    void preparePost() {
        client = HttpClient.newBuilder().build();
        request = HttpRequest.newBuilder()
                .uri(URI.create(Rest.getInstance().createEndpoint(apiCall)))
                .POST(HttpRequest.BodyPublishers.ofByteArray(buildPostKeyXmlEntity()))
                .build();
    }

    private byte[] buildPostKeyXmlEntity() {
        try {
            return buildXml().getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new WrongXml();
        }
    }

    private String buildXml() {
        return XmlMarshaller.getInstance().marshall(o);
    }

    public void postCommand() {
        try {
            executeCommand();
        }
        catch (IOException | InterruptedException e) {
            throw new PostExecutionFailure(e.getMessage());
        }
    }

    private void executeCommand() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Rest.checkResponseCode(response);
        checkForNegativeResponse(response);
    }

    private void checkForNegativeResponse(HttpResponse<String> response) {
        if (!response.body().contains(apiCall)) {
            LOG.error("Negative response received: " + response.body());
        }
    }

    private BufferedReader getHttpResponseReader(InputStream httpEntityContent) {
        return new BufferedReader(new InputStreamReader(httpEntityContent));
    }

    protected void setValue(T value) {
        this.o = value;
        clazz = value.getClass();
    }
}

class WrongXml extends RuntimeException {
    private static final long serialVersionUID = -6436934450037991966L;

    WrongXml(){super();}
    WrongXml(Throwable e) {
        super(e);
    }
}
