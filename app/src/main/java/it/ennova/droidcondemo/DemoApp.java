package it.ennova.droidcondemo;

import android.app.Application;

import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.storage.BaseStorage;

public class DemoApp extends Application{
    private static DemoApp instance;
    private static BaseStorage<MultimediaFile> photosList = new BaseStorage<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DemoApp getInstance() {
       return instance;
    }

    public BaseStorage<MultimediaFile> getPhotoListInstance() {
        return photosList;
    }


}
