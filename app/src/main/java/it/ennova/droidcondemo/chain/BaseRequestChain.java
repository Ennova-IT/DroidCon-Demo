package it.ennova.droidcondemo.chain;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import it.ennova.droidcondemo.common.Requests;

public abstract class BaseRequestChain implements Requests{

    private final String supportedPath;
    private BaseRequestChain next;
    protected Gson gson;

    protected BaseRequestChain(@NonNull String supportedPath) {
        this.supportedPath = supportedPath;
        gson = new Gson();
    }

    public BaseRequestChain setNext(@NonNull BaseRequestChain next) {
        this.next = next;
        return this;
    }

    public void onNewRequest(@NonNull String requestedPath,
                             @NonNull AsyncHttpServerRequest request,
                             @NonNull AsyncHttpServerResponse response) {

        if (requestedPath.equals(supportedPath)) {
            manageRequest(request, response);
        } else if (next != null) {
            next.onNewRequest(requestedPath, request, response);
        }

    }

    protected abstract void manageRequest(@NonNull AsyncHttpServerRequest request,
                                          @NonNull AsyncHttpServerResponse response);
}
