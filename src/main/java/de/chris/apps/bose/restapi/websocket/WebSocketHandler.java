package de.chris.apps.bose.restapi.websocket;

import de.chris.apps.bose.xml.DeviceUpdateCallback;
import de.chris.apps.bose.xml.Updates;
import de.chris.apps.bose.xml.util.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

class WebSocketHandler implements Listener {

    private final static Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);

    private final DeviceUpdateCallback callback;
    private final ExecutorService executor;

    public WebSocketHandler(ExecutorService executor, DeviceUpdateCallback callback) {
        this.executor = executor;
        this.callback = callback;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        LOG.info("CONNECTED");
        Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        LOG.debug("onText received with data " + data);
        Updates deviceUpdate = XmlMarshaller.getInstance().unmarshall(Updates.class, data.toString());
        callback.onMessage(deviceUpdate);
        return Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        LOG.info("Closed with status " + statusCode + ", reason: " + reason);
        executor.shutdown();
        return Listener.super.onClose(webSocket, statusCode, reason);
    }
}
