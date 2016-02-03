package it.ennova.droidcondemo.storage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import it.ennova.droidcondemo.BuildConfig;
import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Observable;

public class ClientWorkerFragment extends ObservableHeadlessFragment<NetworkServiceDiscoveryInfo> {

    private final static String TAG = "ClientWorkerFragment";

    private boolean isChecked = false;

    public static ClientWorkerFragment getWorkerFragmentFrom(@NonNull AppCompatActivity hostActivity) {

        final FragmentManager fragmentManager = hostActivity.getSupportFragmentManager();
        ClientWorkerFragment worker = (ClientWorkerFragment) fragmentManager.findFragmentByTag(TAG);

        if (worker == null) {
            worker = new ClientWorkerFragment();
            fragmentManager.beginTransaction().add(worker, TAG).commit();
        }

        return worker;
    }

    @Override
    protected Observable<NetworkServiceDiscoveryInfo> buildInternalObservable() {
        return ZeRXconf.startDiscovery(getActivity(), BuildConfig.SERVICE_LAYER);
    }

    public void saveCheckedStatus(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean restoreCheckedStatus() {
        return isChecked;
    }
}
