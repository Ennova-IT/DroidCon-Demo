package it.ennova.droidcondemo.common;

import android.support.annotation.IntDef;


public interface ServerData {
    int MOBILE = 0;
    int TABLET = 1;
    int ANDROID = 0;

    @IntDef({ServerData.MOBILE, TABLET})
    @interface Type {}

    @IntDef({ANDROID})
    @interface Platform {}
}
