package it.ennova.droidcondemo.ui.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.ennova.droidcondemo.R;
import it.ennova.droidcondemo.common.ServerData;
import it.ennova.droidcondemo.model.Server;


public class ServerView extends RelativeLayout implements ServerData{

    private TextView label, os;
    private ImageView type, osIcon;

    public ServerView(Context context) {
        this(context, null, 0);
    }

    public ServerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ServerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.server_view, this, true);

            label = (TextView) findViewById(R.id.serverName);
            type = (ImageView) findViewById(R.id.serverIcon);
            os = (TextView) findViewById(R.id.serverOS);
            osIcon = (ImageView) findViewById(R.id.serverOsIcon);
        }
    }

    public void setServer(@NonNull Server server) {
        label.setText(server.getName());
        type.setImageResource(getImageFrom(server.getType()));
        os.setText(server.getVersion());
        osIcon.setImageResource(getOsIconFrom(server.getPlatform()));
    }

    @DrawableRes
    private int getImageFrom(@Type int deviceType) {
        switch(deviceType) {
            case MOBILE:
                return R.drawable.ic_phone;
            case TABLET:
                return R.drawable.ic_tablet;
            default:
                return R.drawable.ic_tablet;
        }
    }

    @DrawableRes
    private int getOsIconFrom(@Platform int osType) {

        return R.drawable.ic_android;
    }
}
