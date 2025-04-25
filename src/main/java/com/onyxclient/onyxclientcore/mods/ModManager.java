package com.onyxclient.onyxclientcore.mods;

import java.util.ArrayList;
import java.util.List;

public class ModManager {
    private static ModManager instance;
    private final List<Mod> mods;

    public static ModManager getInstance() {
        if (instance == null) {
            instance = new ModManager();
        }
        return instance;
    }

    private ModManager() {
        mods = new ArrayList<Mod>();
    }

    public void register(Mod mod) {
        mods.add(mod);
    }

    public boolean toggleMod(Mod mod) {
        mod.enabled = !mod.enabled;
        if (mod.enabled) mod.init();
        return mod.enabled;
    }

    public void update() {
        for (Mod mod : mods) {
            if (mod.enabled) {
                mod.update();
            }
        }
    }

    public List<Mod> getMods() {
        return mods;
    }
}
