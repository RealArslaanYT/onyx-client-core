package com.onyxclient.onyxclientcore.mods.impl;

import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import com.onyxclient.onyxclientcore.mods.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;


public class Freelook extends Mod {
    public Freelook() {
        this.name = "Freelook";
        this.description = "Allows you to look freely in third person!";
        this.enabled = false;
    }

    public boolean isFreelooking = false;
    private Perspective lastPerspective;

    public void startFreelooking() {
        MinecraftClient client = MinecraftClient.getInstance();
        lastPerspective = client.options.getPerspective();

        if (lastPerspective == Perspective.FIRST_PERSON) {
            client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
        isFreelooking = true;
    }

    public void stopFreelooking() {
        isFreelooking = false;
        MinecraftClient client = MinecraftClient.getInstance();
        if (lastPerspective != client.options.getPerspective()) client.options.setPerspective(lastPerspective);
    }

    public void update() {
        if (OnyxClientCoreClient.freelookKeybinding.isPressed()) {
            if (!this.isFreelooking) startFreelooking();
        }
        else {
            if (this.isFreelooking) stopFreelooking();
        }
    }

    @Override
    public void close() {
        stopFreelooking();
    }
}
