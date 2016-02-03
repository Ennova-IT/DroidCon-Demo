package it.ennova.droidcondemo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import it.ennova.droidcondemo.ui.detail.OnItemClickListener;

public class AdapterManager {

    public static <T extends RecyclerView.Adapter> T with(@NonNull RecyclerView recyclerView,
                                                          @NonNull Context context,
                                                          @NonNull T adapter,
                                                          boolean isReverse) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, isReverse);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return adapter;
    }
}
