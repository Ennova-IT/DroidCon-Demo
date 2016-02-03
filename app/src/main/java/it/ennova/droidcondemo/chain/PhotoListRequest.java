package it.ennova.droidcondemo.chain;

import android.support.annotation.NonNull;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import it.ennova.droidcondemo.DemoApp;


public class PhotoListRequest extends BaseRequestChain{

    public PhotoListRequest() {
        super(PHOTOS_LIST_PATH);
    }

    @Override
    protected void manageRequest(@NonNull AsyncHttpServerRequest request, @NonNull AsyncHttpServerResponse response) {
        response.send("application/json", gson.toJson(DemoApp.getInstance().getRetrievedPhotos()));
    }
}
