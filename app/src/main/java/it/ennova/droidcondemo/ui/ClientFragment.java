package it.ennova.droidcondemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import it.ennova.droidcondemo.R;
import it.ennova.droidcondemo.storage.ClientWorkerFragment;
import it.ennova.droidcondemo.ui.client.ClientAdapter;
import it.ennova.droidcondemo.ui.client.ClientAdapterManager;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Subscription;

public class ClientFragment extends RxFragment {

    private Subscription subscription;
    private ClientAdapter adapter;
    private ClientWorkerFragment workerFragment;

    @Bind(R.id.foundServersViewFlipper)
    ViewFlipper foundServersViewFlipper;
    @Bind(R.id.serverList)
    RecyclerView recyclerView;
    @Bind(R.id.discoverServicesSwitch)
    SwitchCompat discoverServicesSwitches;

    public ClientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = ClientAdapterManager.with(recyclerView, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        getSecurelyWorkerFragment();
    }

    private ClientWorkerFragment getSecurelyWorkerFragment() {
        if (workerFragment == null) {
            workerFragment = ClientWorkerFragment.getWorkerFragmentFrom((AppCompatActivity) getActivity());
        }
        return workerFragment;
    }

    @OnCheckedChanged(R.id.discoverServicesSwitch)
    public void onCheckedChange(boolean isChecked) {
        if (isChecked) {
            subscription = getSecurelyWorkerFragment().getObservable().subscribe(this::onNext, this::onError);
        } else {
            unsubscribe();
            getActivity().runOnUiThread(() -> adapter.clear());
        }
    }

    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        workerFragment.saveCheckedStatus(discoverServicesSwitches.isChecked());
        unsubscribe();
    }

    public void onNext(@NonNull NetworkServiceDiscoveryInfo info) {
        getActivity().runOnUiThread(() -> {
            if (foundServersViewFlipper.getDisplayedChild() == 0) {
                foundServersViewFlipper.showNext();
            }
            adapter.onNetworkRetrieved(info);
        });
    }


    public void onError(@NonNull Throwable throwable) {
        Log.d("DroidCon Demo", throwable.getMessage());
    }
}
