package it.ennova.droidcondemo.chain;

import android.support.annotation.NonNull;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import it.ennova.droidcondemo.DemoApp;
import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.storage.BaseStorage;


public class PhotoListRequest extends BaseRequestChain{
    BaseStorage<MultimediaFile> photos;

    public PhotoListRequest() {
        super(PHOTOS_LIST_PATH);
        photos = DemoApp.getInstance().getPhotoListInstance();
    }

    @Override
    protected void manageRequest(@NonNull AsyncHttpServerRequest request, @NonNull AsyncHttpServerResponse response) {
        response.send("application/json", gson.toJson(photos.get()));
    }
}
