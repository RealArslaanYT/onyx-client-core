package com.onyxclient.onyxclientcore.mods.impl;

import com.onyxclient.onyxclientcore.mods.Mod;
import net.minecraft.client.MinecraftClient;


public class Freelook extends Mod {
    public Freelook() {
        this.name = "Freelook";
        this.description = "Allows you to look freely in third person!";
        this.enabled = false;
    }

    public void init() {

    }

    public void update() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.cameraEntity == null) return;
        double mouseX = client.mouse.getX();
        float sensitivity = 0.03F;

        float yaw = client.cameraEntity.getYaw() + (float) mouseX * sensitivity;

        client.cameraEntity.setYaw(yaw);
    }
}
