package com.onyxclient.onyxclientcore.mods;

import com.onyxclient.onyxclientcore.OnyxClientCore;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class ModManager {
    private static ModManager instance;
    private final List<Mod> mods;
    private static final String CONFIG_FILE = "OnyxClient.properties";

    public static ModManager getInstance() {
        if (instance == null) {
            instance = new ModManager();
        }
        return instance;
    }

    private ModManager() {
        mods = new ArrayList<>();
    }

    public void register(Mod mod) {
        mods.add(mod);
    }

    public boolean toggleMod(Mod mod) {
        mod.setEnabled(!mod.isEnabled());
        if (mod.isEnabled()) {
            mod.init();
        } else {
            mod.close();
        }
        saveModStates();
        return mod.isEnabled();
    }

    public void update() {
        for (Mod mod : mods) {
            if (mod.isEnabled()) {
                mod.update();
            }
        }
    }

    public void close() {
        for (Mod mod : mods) {
            mod.close();
        }
        saveModStates();
    }

    public List<Mod> getMods() {
        return mods;
    }

    public void loadModStates() {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(fis);
                for (Mod mod : mods) {
                    String modName = mod.getName();
                    String state = properties.getProperty(modName);
                    if (state != null && state.equals("true")) {
                        mod.setEnabled(true);
                        mod.init();
                    } else {
                        mod.setEnabled(false);
                    }
                }
            } catch (IOException e) {
                OnyxClientCore.LOGGER.info("Failed to load mod states");
            }
        }
    }

    private void saveModStates() {
        Properties properties = new Properties();
        for (Mod mod : mods) {
            properties.setProperty(mod.getName(), String.valueOf(mod.isEnabled()));
        }
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Onyx Client - Mod States");
        } catch (IOException e) {
            OnyxClientCore.LOGGER.error("Failed to save mod states");
        }
    }
}
