package com.onyxclient.onyxclientcore.mods.impl;

import com.onyxclient.onyxclientcore.mods.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;


public class Freelook extends Mod {
    public Freelook() {
        this.name = "Freelook";
        this.description = "Allows you to look freely in third person!";
        this.enabled = false;
    }

    private Perspective lastPerspective;

    @Override
    public void init() {
        MinecraftClient client = MinecraftClient.getInstance();
        lastPerspective = client.options.getPerspective();

        if (lastPerspective == Perspective.FIRST_PERSON) {
            client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }

    @Override
    public void close() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (lastPerspective != client.options.getPerspective()) client.options.setPerspective(lastPerspective);
    }
}
