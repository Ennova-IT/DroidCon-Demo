package it.ennova.droidcondemo.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import it.ennova.droidcondemo.R;
import it.ennova.droidcondemo.model.MultimediaFile;

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private final MultimediaFile[] images;
    private WeakReference<OnItemClickListener<MultimediaFile>> weakListener;

    public DetailAdapter(@NonNull MultimediaFile[] images,
                         @NonNull WeakReference<OnItemClickListener<MultimediaFile>> weakListener) {

        this.images = images;
        this.weakListener = weakListener;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DetailViewHolder(inflater.inflate(R.layout.photo_item, parent, false), weakListener);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.bindTo(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
