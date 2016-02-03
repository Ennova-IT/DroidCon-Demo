package it.ennova.droidcondemo.factories;

import android.os.Build;

public class DeviceNameFactory {
    private static String deviceName = null;

    static {
        deviceName = buildDeviceName();
    }

    public static String getDeviceName() {
        return deviceName;
    }

    private static String buildDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
