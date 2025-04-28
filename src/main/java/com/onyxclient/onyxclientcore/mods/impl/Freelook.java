package com.onyxclient.onyxclientcore.mods.impl;

import com.onyxclient.onyxclientcore.FreelookCameraAccess;
import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import com.onyxclient.onyxclientcore.mods.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;


public class Freelook extends Mod {
    public Freelook() {
        this.name = "Freelook";
        this.description = "Allows you to look freely in third person!";
        this.enabled = false;
    }

    private boolean hudEventRegistered = false;
    private boolean wasCinematicCamera = false;
    public boolean isFreelooking = false;
    private Perspective lastPerspective = Perspective.FIRST_PERSON;

    public void startFreelooking() {
        MinecraftClient client = MinecraftClient.getInstance();
        lastPerspective = client.options.getPerspective();

        if (lastPerspective == Perspective.FIRST_PERSON) {
            client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }

        if (client.options.smoothCameraEnabled) {
            wasCinematicCamera = true;
            client.options.smoothCameraEnabled = false;
        }

        isFreelooking = true;
    }

    public void stopFreelooking() {
        isFreelooking = false;
        MinecraftClient client = MinecraftClient.getInstance();
        if (lastPerspective != client.options.getPerspective()) client.options.setPerspective(lastPerspective);
        if (wasCinematicCamera) {
            client.options.smoothCameraEnabled = true;
            wasCinematicCamera = false;
        }
    }

    private void onHudRender(DrawContext context, RenderTickCounter renderTickCounter) {
        if (MinecraftClient.getInstance().player == null) return;
        if (!this.isFreelooking) return;
        if (!isEnabled()) return;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String text = "Freelook is active!";
        int textWidth = textRenderer.getWidth(text);
        int width = context.getScaledWindowWidth();
        int textX = (width / 2) - (textWidth / 2);
        float alpha = 1.0F;
        int i = MathHelper.ceil(alpha * 255.0F) << 24;
        context.drawTextWithShadow(textRenderer, Text.of(text), textX, 2, 0x00FF00 | i);
    }

    public void init() {
        if (hudEventRegistered) return;
        HudRenderCallback.EVENT.register(this::onHudRender);
        hudEventRegistered = true;
    }

    public void update() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (OnyxClientCoreClient.freelookKeybinding.isPressed()) {
            if (!this.isFreelooking) startFreelooking();
        }
        else {
            if (this.isFreelooking) stopFreelooking();
        }

        if (!this.isFreelooking && player != null) {
            ((FreelookCameraAccess) player).setFreelookYaw(player.getYaw());
        }
    }

    @Override
    public void close() {
        stopFreelooking();
    }
}
