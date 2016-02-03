package it.ennova.droidcondemo.ui.detail;

import android.support.annotation.NonNull;

public interface OnItemClickListener<T> {

    void onItemClicked(@NonNull T selectedItem);
}
