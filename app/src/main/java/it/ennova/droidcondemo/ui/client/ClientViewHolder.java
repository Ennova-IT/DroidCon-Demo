package it.ennova.droidcondemo.ui.client;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import it.ennova.droidcondemo.DetailActivity;
import it.ennova.droidcondemo.R;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;

public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView deviceLabel, ipLabel;
    private NetworkServiceDiscoveryInfo info;

    public ClientViewHolder(View itemView) {
        super(itemView);
        deviceLabel = (TextView) itemView.findViewById(R.id.serverName);
        ipLabel = (TextView) itemView.findViewById(R.id.serverIp);

        itemView.setOnClickListener(this);
    }

    public void bindTo(@NonNull NetworkServiceDiscoveryInfo info) {
        this.info = info;
        deviceLabel.setText(info.getServiceName());
        ipLabel.setText(String.format(Locale.getDefault(), "%s:%d", info.getAddress(), info.getServicePort()));
    }

    @Override
    public void onClick(View v) {
        DetailActivity.startWith(info);
    }
}
