package it.ennova.droidcondemo.storage;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.util.HashMap;
import java.util.List;

import it.ennova.droidcondemo.BuildConfig;
import it.ennova.droidcondemo.DemoApp;
import it.ennova.droidcondemo.callbacks.OnConnectionReceivedListener;
import it.ennova.droidcondemo.common.Requests;
import it.ennova.droidcondemo.factories.DeviceNameFactory;
import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.droidcondemo.providers.PhotoProvider;
import it.ennova.droidcondemo.providers.ServerProvider;
import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Observable;

public class ServerWorkerFragment extends ObservableHeadlessFragment<NetworkServiceDiscoveryInfo> implements OnConnectionReceivedListener, Requests {
    private ServerProvider provider;
    private boolean isServerStarted = false;
    private OnConnectionListener masterListener;
    private Pair<List<AsyncHttpServerRequest>, List<String>> requests;
    private BaseStorage<MultimediaFile> photosList = DemoApp.getInstance().getPhotoListInstance();

    private final static String TAG = "ServerWorkerFragment";

    public static ServerWorkerFragment getWorkerFragmentFrom(@NonNull AppCompatActivity hostActivity) {

        final FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();
        ServerWorkerFragment worker = (ServerWorkerFragment) fragmentManager.findFragmentByTag(TAG);

        if (worker == null) {
            worker = new ServerWorkerFragment();
            fragmentManager.beginTransaction().add(worker, TAG).commit();
        }

        return worker;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provider = ServerProvider.with(this);
    }

    public void attachTo(@NonNull OnConnectionListener masterListener) {
        this.masterListener = masterListener;
    }

    @Override
    protected Observable<NetworkServiceDiscoveryInfo> buildInternalObservable() {
        return ZeRXconf.advertise(getContext(), DeviceNameFactory.getDeviceName(),
                BuildConfig.SERVICE_LAYER, provider.getPort(), new HashMap<>(0));
    }

    @Override
    public void onConnectionReceived(@NonNull String requestedPath, @NonNull AsyncHttpServerRequest request,
                                     @NonNull AsyncHttpServerResponse response) {
        if (masterListener != null) {
            new Handler(Looper.getMainLooper()).post(() -> masterListener.onConnectionReceived(requestedPath, request, response));
        }
    }

    public void startServer() {
        provider.start();
        preparePhotos();
        isServerStarted = true;
    }

    public void stopServer() {
        provider.stop();
        isServerStarted = false;
        resetObservable();
    }

    private void preparePhotos() {
        if (photosList.isEmpty()) {
            PhotoProvider.getInstance().getCameraImagesFrom(getActivity()).subscribe(this::onPhotoRetrieved,
                    throwable -> Log.e(TAG, throwable.getMessage()), this::onPhotoRetrievalCompleted);
        }
    }

    private void onPhotoRetrieved(@NonNull MultimediaFile file) {
        Log.d(TAG, "New file found: " + file.getFileName());
        photosList.add(file);
    }

    private void onPhotoRetrievalCompleted() {
        Log.d(TAG, "All files have been retrieved");
    }

    public boolean isServerStarted() {
        return isServerStarted;
    }

    public void saveRequestsFrom(@NonNull Pair<List<AsyncHttpServerRequest>, List<String>> requests) {
        this.requests = requests;
    }

    public Pair<List<AsyncHttpServerRequest>, List<String>> restoreRequests() {
        return requests;
    }

    public interface OnConnectionListener {
        @UiThread
        void onConnectionReceived(@NonNull String requestedPath, @NonNull AsyncHttpServerRequest request,
                                  @NonNull AsyncHttpServerResponse response);
    }


}
