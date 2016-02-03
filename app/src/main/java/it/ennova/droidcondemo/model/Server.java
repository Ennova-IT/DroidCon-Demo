package it.ennova.droidcondemo.model;

import android.support.annotation.NonNull;

import it.ennova.droidcondemo.common.ServerData;

public class Server implements ServerData {

    private final String name;
    private final int platform;
    private final String version;
    private final int type;

    public Server(@NonNull String name, @Platform int platform,
                  @NonNull String version, @Type int type) {

        this.name = name;
        this.platform = platform;
        this.version = version;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Platform
    public int getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    @Type
    public int getType() {
        return type;
    }
}
