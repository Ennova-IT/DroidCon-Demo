package it.ennova.droidcondemo.ui.detail;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;

import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.ui.AdapterManager;

public class DetailAdapterManager {

    public static DetailAdapter with (@NonNull Context context,
                                      @NonNull RecyclerView recyclerView,
                                      @NonNull OnItemClickListener<MultimediaFile> listener,
                                      @NonNull MultimediaFile[] multimediaFiles) {

        return AdapterManager.with(recyclerView, context, new DetailAdapter(multimediaFiles,
                new WeakReference<>(listener)), false);

    }

    public static DetailAdapter with (@NonNull Context context,
                                      @NonNull RecyclerView recyclerView,
                                      @NonNull DetailAdapter adapter) {

        return AdapterManager.with(recyclerView, context, adapter, false);
    }
}
