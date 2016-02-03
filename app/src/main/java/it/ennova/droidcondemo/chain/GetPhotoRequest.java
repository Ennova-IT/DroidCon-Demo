package it.ennova.droidcondemo.chain;

import android.support.annotation.NonNull;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class GetPhotoRequest extends BaseRequestChain{
    protected GetPhotoRequest() {
        super(GET_PHOTO);
    }

    @Override
    protected void manageRequest(@NonNull AsyncHttpServerRequest request, @NonNull AsyncHttpServerResponse response) {
        String path = extractDecodedImagePathFrom(request);
        response.sendFile(new File(path));
    }

    private String extractDecodedImagePathFrom(@NonNull AsyncHttpServerRequest request) {
        try {
            return URLDecoder.decode(request.getQuery().getString(GET_PHOTO_PARAMETER_FULL_PATH), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
