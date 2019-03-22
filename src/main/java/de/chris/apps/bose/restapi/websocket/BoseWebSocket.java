package de.chris.apps.bose.restapi.websocket;

import de.chris.apps.bose.restapi.Rest;
import de.chris.apps.bose.xml.DeviceUpdateCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Builder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoseWebSocket implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(BoseWebSocket.class);

    private final ExecutorService executor;
    private WebSocket webSocket;

    public BoseWebSocket(DeviceUpdateCallback callback) {
        this.executor = Executors.newSingleThreadExecutor();
        connect(callback);
    }

    private void connect(DeviceUpdateCallback callback) {
        HttpClient httpClient = HttpClient.newBuilder().executor(executor).build();
        Builder webSocketBuilder = httpClient
                .newWebSocketBuilder()
                .subprotocols(Rest.getInstance().getConstants().getProtocol());
        webSocket = webSocketBuilder.buildAsync(URI.create(Rest.getInstance().getConstants().getWebsocket()),
                new WebSocketHandler(executor, callback)).join();
    }

    @Override
    public void close() {
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").
                thenRun(() -> LOG.info("Sent close"));
    }
}
