package it.ennova.droidcondemo.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import it.ennova.droidcondemo.DemoApp;
import it.ennova.droidcondemo.R;
import it.ennova.droidcondemo.model.MultimediaFile;


public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView imageName, imageSize;
    private WeakReference<OnItemClickListener<MultimediaFile>> listener;
    private MultimediaFile multimediaFile;

    public DetailViewHolder(@NonNull View itemView,
                            @NonNull WeakReference<OnItemClickListener<MultimediaFile>> listener) {
        super(itemView);
        this.listener = listener;
        imageName = (TextView) itemView.findViewById(R.id.imageName);
        imageSize = (TextView) itemView.findViewById(R.id.imageSize);
        itemView.setOnClickListener(this);
    }

    public void bindTo(@NonNull MultimediaFile multimediaFile) {
        this.multimediaFile = multimediaFile;
        imageName.setText(multimediaFile.getFileName());
        imageSize.setText(Formatter.formatShortFileSize(DemoApp.getInstance(), multimediaFile.getFileSize()));
    }

    @Override
    public void onClick(View v) {
        listener.get().onItemClicked(multimediaFile);
    }
}
