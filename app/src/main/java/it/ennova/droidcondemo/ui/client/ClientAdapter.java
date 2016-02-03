package it.ennova.droidcondemo.ui.client;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.ennova.droidcondemo.R;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder> {
    private List<NetworkServiceDiscoveryInfo> networks = new ArrayList<>();

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ClientViewHolder(inflater.inflate(R.layout.server_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        holder.bindTo(networks.get(position));
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public void onNetworkRetrieved(@NonNull NetworkServiceDiscoveryInfo info) {
        if (info.isAdded()) {
            addInfoTo(info);
        } else {
            removeInfoFrom(info);
        }

        notifyDataSetChanged();
    }

    private void removeInfoFrom(NetworkServiceDiscoveryInfo info) {
        int index = networks.indexOf(info);
        if (index >= 0) {
            networks.remove(index);
            notifyItemRemoved(index);
        }
    }

    private void addInfoTo(@NonNull NetworkServiceDiscoveryInfo info) {
        networks.add(info);
        notifyItemInserted(networks.size() - 1);
    }

    public void clear() {
        networks.clear();
        notifyDataSetChanged();
    }
}
