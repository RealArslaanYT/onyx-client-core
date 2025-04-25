package com.onyxclient.onyxclientcore.mods;

public class Mod {
    public static String name;
    public static String description;
    public boolean enabled = false;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void init() {

    }

    public void update() {
        // Runs every tick
    }
}
