package it.ennova.droidcondemo.storage;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BaseStorage<T> {

    private List<T> cache = new ArrayList<>();
    private T[] temporary = (T[]) new Object[0];

    public synchronized void add(@NonNull T data) {
        cache.add(data);
    }

    public T[] get() {
        return cache.toArray(temporary);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
