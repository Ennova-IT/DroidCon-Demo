package it.ennova.droidcondemo.chain;

import android.support.annotation.NonNull;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import it.ennova.droidcondemo.factories.ServerFactory;


public class RootRequest extends BaseRequestChain{

    private final String jsonResponse;

    public RootRequest() {
        super(ROOT_PATH);
        jsonResponse = gson.toJson(ServerFactory.build());
    }

    @Override
    protected void manageRequest(@NonNull AsyncHttpServerRequest request, @NonNull AsyncHttpServerResponse response) {
        response.send("application/json", jsonResponse);
    }
}
