package it.ennova.droidcondemo.factories;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import it.ennova.droidcondemo.DemoApp;
import it.ennova.droidcondemo.common.ServerData;
import it.ennova.droidcondemo.model.Server;

public class ServerFactory {

    public static Server build() {
        return new Server(DeviceNameFactory.getDeviceName(), ServerData.ANDROID, Build.VERSION.RELEASE, getTypeIcon(DemoApp.getInstance()));
    }

    @Server.Type
    private static int getTypeIcon(@NonNull final Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return Server.TABLET;
        } else {
            return Server.MOBILE;
        }
    }
}
