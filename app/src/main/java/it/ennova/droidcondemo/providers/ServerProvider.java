package it.ennova.droidcondemo.providers;

import android.support.annotation.NonNull;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;

import it.ennova.droidcondemo.callbacks.OnConnectionReceivedListener;
import it.ennova.droidcondemo.common.Requests;

public class ServerProvider implements Requests{

    private AsyncHttpServer server;
    private final int port;
    private WeakReference<OnConnectionReceivedListener> weakListener;

    public static ServerProvider with (@NonNull OnConnectionReceivedListener listener) {
        return new ServerProvider(new WeakReference<>(listener));
    }

    ServerProvider(WeakReference<OnConnectionReceivedListener> weakListener) {
        this.weakListener = weakListener;

        server = new AsyncHttpServer();
        server.get(ROOT_PATH, this::onRootRequestReceived);
        server.get(PHOTOS_LIST_PATH, this::onPhotoRequestReceived);
        server.get(GET_PHOTO, this::onGetPhotoRequested);

        port = findFreePortOnDevice();
    }

    private int findFreePortOnDevice() {
        try {
            ServerSocket socket = new ServerSocket(0);
            int port = socket.getLocalPort();
            socket.close();
            return port;
        } catch (IOException e) {
            return FALLBACK_PORT;
        }
    }

    public int getPort() {
        return port;
    }

    public void start() {
        server.listen(port);
    }

    private void onRootRequestReceived(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        weakListener.get().onConnectionReceived(ROOT_PATH, request, response);
    }

    private void onPhotoRequestReceived(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        weakListener.get().onConnectionReceived(PHOTOS_LIST_PATH, request, response);
    }

    private void onGetPhotoRequested(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        weakListener.get().onConnectionReceived(GET_PHOTO, request, response);
    }

    public void stop() {
        AsyncServer.getDefault().stop();
    }
}
