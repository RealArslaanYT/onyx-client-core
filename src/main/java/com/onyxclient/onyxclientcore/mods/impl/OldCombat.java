package com.onyxclient.onyxclientcore.mods.impl;

import com.onyxclient.onyxclientcore.OnyxClientCore;
import com.onyxclient.onyxclientcore.mods.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class OldCombat extends Mod {
    public OldCombat() {
        this.name = "1.8-style Combat";
        this.description = "Brings back the old combat style from pre-1.9! (May get you banned on some servers.)";
        this.setEnabled(false);
    }

    @Override
    public void init() {
        OnyxClientCore.LOGGER.info("OldCombat - mod initialized!");
        if (MinecraftClient.getInstance().player != null) MinecraftClient.getInstance().player.sendMessage(Text.of("Warning! - The 1.8-style Combat mod may result in a ban on some servers!"), true);
    }

    @Override
    public void update() {

    }
}