package it.ennova.droidcondemo.ui.server;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.ennova.droidcondemo.R;

public class ServerRequestAdapter extends RecyclerView.Adapter<ServerRequestViewHolder> {
    private List<AsyncHttpServerRequest> requests = new ArrayList<>();
    private List<String> timestamps = new ArrayList<>();
    private final SimpleDateFormat template = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    public ServerRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ServerRequestViewHolder(inflater.inflate(R.layout.incoming_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ServerRequestViewHolder holder, int position) {
        holder.bindTo(requests.get(position), timestamps.get(position));
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void onNewRequestReceived(@NonNull AsyncHttpServerRequest request) {
        requests.add(request);
        timestamps.add(template.format(new Date()));
        notifyItemInserted(requests.size() - 1);
        notifyDataSetChanged();
    }

    public Pair<List<AsyncHttpServerRequest>, List<String>> dumpRequests() {
        return new Pair<>(requests, timestamps);
    }

    public void onMultipleRequestReceived(@NonNull Pair<List<AsyncHttpServerRequest>, List<String>> dumps) {

        requests.addAll(dumps.first);
        timestamps.addAll(dumps.second);
        notifyDataSetChanged();
    }
}
