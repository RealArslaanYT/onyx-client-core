package com.onyxclient.onyxclientcore.mods;

public class Mod {
    public String name;
    public String description;
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
        // Runs before mod starts
    }

    public void update() {
        // Runs every tick
    }

    public void close() {
        // Runs before/when mod is disabled
    }
}
