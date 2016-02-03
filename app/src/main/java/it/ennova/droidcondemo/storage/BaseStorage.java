package it.ennova.droidcondemo.storage;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BaseStorage<T> {

    private List<T> cache = new ArrayList<>();

    public void add(@NonNull T data) {
        cache.add(data);
    }

    public List<T> get() {
        return cache;
    }
}
