package com.onyxclient.onyxclientcore.mods.impl.hud;

import com.onyxclient.onyxclientcore.mods.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class CoordinatesHUD extends Mod {
    public boolean hudEventRegistered = false;
    public CoordinatesHUD() {
        this.name = "Coordinates HUD";
        this.description = "Shows your coordinates/XYZ position in the HUD!";
        this.enabled = false;
    }

    @Override
    public void init() {
        if (hudEventRegistered) return;
        HudRenderCallback.EVENT.register(this::onHudRender);
        hudEventRegistered = true;
    }

    private void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (MinecraftClient.getInstance().player == null) return;
        if (!isEnabled()) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        String playerX = String.valueOf(Math.round(player.getPos().x));
        String playerY = String.valueOf(Math.round(player.getPos().y));
        String playerZ = String.valueOf(Math.round(player.getPos().z));
        String coordsString = "X: " + playerX + " Y: " + playerY + " Z: " + playerZ;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        float alpha = 1.0F;
        int i = MathHelper.ceil(alpha * 255.0F) << 24;
        drawContext.drawTextWithShadow(textRenderer, Text.of(coordsString), 2, 2, 16777215 | i);
    }
}
