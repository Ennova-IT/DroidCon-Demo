package it.ennova.droidcondemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.ennova.droidcondemo.common.Requests;
import it.ennova.droidcondemo.factories.ObservableFactory;
import it.ennova.droidcondemo.factories.UrlFactory;
import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.ui.detail.DetailAdapterManager;
import it.ennova.droidcondemo.ui.detail.OnItemClickListener;
import it.ennova.droidcondemo.ui.views.ServerView;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity implements OnItemClickListener<MultimediaFile>, Requests{
    private static final String KEY = "NetworkInfo";
    private NetworkServiceDiscoveryInfo info;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.serverData)
    ServerView serverData;

    @Bind(R.id.serversPhotoList)
    RecyclerView photoList;

    public static void startWith(@NonNull NetworkServiceDiscoveryInfo info) {
        Intent intent = new Intent(DemoApp.getInstance(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY, info);
        DemoApp.getInstance().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        info = getIntent().getParcelableExtra(KEY);

        buildUi();
    }

    private void buildUi() {
        ObservableFactory.getRootDataFrom(UrlFactory.from(info))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(serverData::setServer, this::onError);

        ObservableFactory.getPhotosListFrom(UrlFactory.from(info, PHOTOS_LIST_PATH))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onPhotosRetrieved, this::onError);
    }

    private void onPhotosRetrieved(MultimediaFile[] files) {

        DetailAdapterManager.with(this, photoList, this, files);
    }
    private void onError(@NonNull Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(@NonNull MultimediaFile selectedItem) {
        ObservableFactory.getPhotoFrom(UrlFactory.from(info, GET_PHOTO, selectedItem))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onPhotoReceived, this::onError);
    }

    private void onPhotoReceived(Bitmap bitmap) {

        ImageView rootView = new ImageView(this);
        rootView.setImageBitmap(bitmap);
        new AlertDialog.Builder(this).setView(rootView).show();
    }
}
