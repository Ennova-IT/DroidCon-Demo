package it.ennova.droidcondemo.ui.server;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;

import it.ennova.droidcondemo.R;

public class ServerRequestViewHolder extends RecyclerView.ViewHolder{

    private TextView userAgentTextView, timestampTextView;

    public ServerRequestViewHolder(View itemView) {
        super(itemView);
        userAgentTextView = (TextView) itemView.findViewById(R.id.itemUserAgent);
        timestampTextView = (TextView) itemView.findViewById(R.id.itemTimestamp);
    }

    public void bindTo(@NonNull AsyncHttpServerRequest request, String timestap) {

        userAgentTextView.setText(request.getHeaders().get("user-agent"));
        timestampTextView.setText(timestap);
    }
}
