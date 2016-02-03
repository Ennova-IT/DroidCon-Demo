package it.ennova.droidcondemo.factories;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import it.ennova.droidcondemo.common.Requests;
import it.ennova.droidcondemo.model.MultimediaFile;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;

public class UrlFactory implements Requests{

    public static String from(@NonNull final NetworkServiceDiscoveryInfo info) {

        final String prefix = "http://";
        String baseUrl = String.format(Locale.getDefault(), "%s:%s", info.getAddress().getHostAddress(), info.getServicePort());

        if (!baseUrl.startsWith(prefix)) {
            baseUrl = prefix + baseUrl;
        }

        return baseUrl;
    }

    public static String from(@NonNull final NetworkServiceDiscoveryInfo info, @NonNull String postfix) {

        if (!postfix.startsWith("/")) {
            postfix = "/" + postfix;
        }

        return from(info) + postfix;
    }

    public static String from(@NonNull final NetworkServiceDiscoveryInfo info,
                              @NonNull String postfix, @NonNull MultimediaFile multimediaFile) {

        return from(info, postfix) + buildQueryFrom(multimediaFile);
    }

    private static String buildQueryFrom(@NonNull MultimediaFile multimediaFile) {

        try {
            return "?" + GET_PHOTO_PARAMETER_FULL_PATH + "=" + URLEncoder.encode(multimediaFile.getFullPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
