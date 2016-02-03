package it.ennova.droidcondemo.factories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import it.ennova.droidcondemo.common.Requests;
import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.model.Server;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import rx.Observable;

public class ObservableFactory implements Requests{

    public static Observable<Server> getRootDataFrom(@NonNull final String url) {

        return Observable.create(subscriber -> {
            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                Server server = gson.fromJson(response.body().charStream(), Server.class);
                subscriber.onNext(server);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public static Observable<MultimediaFile[]> getPhotosListFrom(@NonNull final String url) {
        return Observable.create(subscriber -> {
            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
                MultimediaFile[] multimediaFiles = gson.fromJson(response.body().charStream(), MultimediaFile[].class);
                subscriber.onNext(multimediaFiles);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public static Observable<Bitmap> getPhotoFrom(@NonNull final String url) {
        return Observable.create(subscriber -> {
            Request request = new Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(request).execute();
                subscriber.onNext(BitmapFactory.decodeStream(response.body().byteStream()));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
