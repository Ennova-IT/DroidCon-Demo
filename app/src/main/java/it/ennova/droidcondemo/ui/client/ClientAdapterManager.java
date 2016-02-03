package it.ennova.droidcondemo.ui.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import it.ennova.droidcondemo.ui.AdapterManager;

public class ClientAdapterManager {

    public static ClientAdapter with(@NonNull RecyclerView recyclerView,
                                     @NonNull Context context) {

        return AdapterManager.with(recyclerView, context, new ClientAdapter(), false);
    }
}
