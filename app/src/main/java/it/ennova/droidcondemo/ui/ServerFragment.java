package it.ennova.droidcondemo.ui;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.ennova.droidcondemo.R;
import it.ennova.droidcondemo.chain.BaseRequestChain;
import it.ennova.droidcondemo.chain.ChainManager;
import it.ennova.droidcondemo.storage.ServerWorkerFragment;
import it.ennova.droidcondemo.ui.server.ServerRequestAdapter;
import it.ennova.droidcondemo.ui.server.ServerRequestAdapterManager;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Subscription;

public class ServerFragment extends RxFragment implements ServerWorkerFragment.OnConnectionListener {

    @Bind(R.id.serverViewFlipper)
    ViewFlipper serverViewFlipper;

    @Bind(R.id.serverConnectionsViewFlipper)
    ViewFlipper connectionsViewFlipper;

    @Bind(R.id.requestsList)
    RecyclerView requestsList;

    private Subscription subscription;
    private ServerRequestAdapter adapter;
    private BaseRequestChain chain;

    private ServerWorkerFragment workerFragment;

    public ServerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.server_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViewFlippersAnimations();
        adapter = ServerRequestAdapterManager.with(requestsList, getContext());
        chain = ChainManager.getInstance().getChain();
    }

    private void bindViewFlippersAnimations() {
        bindAnimationsTo(serverViewFlipper, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        bindAnimationsTo(connectionsViewFlipper, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void bindAnimationsTo(@NonNull ViewFlipper v, @AnimRes int in, @AnimRes int out) {
        v.setInAnimation(getActivity(), in);
        v.setOutAnimation(getActivity(), out);
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeWorkerFragment();
        resumeAdapter();
    }

    private void resumeWorkerFragment() {
        workerFragment = ServerWorkerFragment.getWorkerFragmentFrom((AppCompatActivity) getActivity());
        workerFragment.attachTo(this);
        if (workerFragment.isServerStarted()) {
            subscription = workerFragment.getObservable().subscribe(this::updateUiFrom, this::onError);
            serverViewFlipper.showNext();
        }
    }

    private void resumeAdapter() {
        Pair<List<AsyncHttpServerRequest>, List<String>> previousRequests = workerFragment.restoreRequests();
        if (previousRequests != null && !previousRequests.first.isEmpty()) {
            adapter.onMultipleRequestReceived(previousRequests);
            updateRequestVisibility();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        workerFragment.saveRequestsFrom(adapter.dumpRequests());
    }

    @OnClick(R.id.btnStartServer)
    public void onServerStartedClicked() {
        subscription = workerFragment.getObservable().subscribe(this::updateUiFrom, this::onError);
    }

    private void updateUiFrom(@NonNull NetworkServiceDiscoveryInfo info) {
        workerFragment.startServer();
        serverViewFlipper.showNext();
    }

    private void onError(@NonNull Throwable throwable) {
        Log.d("DroidCon Demo", throwable.getMessage());
    }

    @OnClick(R.id.btnStopServer)
    public void onServerStoppedClicked() {
        subscription.unsubscribe();
        workerFragment.stopServer();
        serverViewFlipper.showPrevious();
    }

    @Override
    public void onConnectionReceived(@NonNull String requestedPath,
                                     @NonNull AsyncHttpServerRequest request,
                                     @NonNull AsyncHttpServerResponse response) {

        updateRequestVisibility();
        adapter.onNewRequestReceived(request);
        chain.onNewRequest(requestedPath, request, response);
    }

    private void updateRequestVisibility() {
        if (connectionsViewFlipper.getDisplayedChild() == 0) {
            connectionsViewFlipper.showNext();
        }
    }

}
