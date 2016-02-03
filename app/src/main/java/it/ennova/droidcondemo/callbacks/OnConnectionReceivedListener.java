package it.ennova.droidcondemo.callbacks;

import android.support.annotation.NonNull;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

public interface OnConnectionReceivedListener {

    void onConnectionReceived(@NonNull String requestedPath,
                              @NonNull AsyncHttpServerRequest request,
                              @NonNull AsyncHttpServerResponse response);
}
