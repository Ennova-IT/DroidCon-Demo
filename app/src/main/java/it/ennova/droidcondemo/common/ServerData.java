package it.ennova.droidcondemo.common;

import android.support.annotation.IntDef;

/**
 * Created by roberto on 01/02/16.
 */
public interface ServerData {
    int MOBILE = 0;
    int TABLET = 1;
    int ANDROID = 0;

    @IntDef({ServerData.MOBILE, TABLET})
    public @interface Type {}

    @IntDef({ANDROID})
    public @interface Platform {}
}
