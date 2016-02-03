package it.ennova.droidcondemo;

import android.app.Application;
import android.support.annotation.NonNull;

import java.util.List;

import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.storage.BaseStorage;

public class DemoApp extends Application{
    private static DemoApp instance;
    private BaseStorage<MultimediaFile> photosList = new BaseStorage<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DemoApp getInstance() {
       return instance;
    }

    public void addRetrievedPhoto(@NonNull MultimediaFile photo) {
        photosList.add(photo);
    }

    public List<MultimediaFile> getRetrievedPhotos() {
        return photosList.get();
    }
}
