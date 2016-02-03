package it.ennova.droidcondemo.ui.server;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import it.ennova.droidcondemo.ui.AdapterManager;

public class ServerRequestAdapterManager {

    public static ServerRequestAdapter with(@NonNull RecyclerView recyclerView,
                                            @NonNull Context context) {

        return AdapterManager.with(recyclerView, context, new ServerRequestAdapter(), true);
    }
}
