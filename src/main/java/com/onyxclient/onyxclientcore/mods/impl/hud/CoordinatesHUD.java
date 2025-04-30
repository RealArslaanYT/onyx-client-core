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
    private boolean hudEventRegistered = false;
    public CoordinatesHUD() {
        this.name = "Coordinates HUD";
        this.description = "Shows your coordinates/XYZ position and your FPS in the HUD!";
        this.setEnabled(false);
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
        String coords = "X: " + playerX + " Y: " + playerY + " Z: " + playerZ;
        String fps = MinecraftClient.getInstance().getCurrentFps() + " fps";

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        float alpha = 1.0F;
        int i = MathHelper.ceil(alpha * 255.0F) << 24;
        int fontHeight = textRenderer.fontHeight;
        drawContext.drawTextWithShadow(textRenderer, Text.of(coords), 2, 2, 16777215 | i);
        drawContext.drawTextWithShadow(textRenderer, Text.of(fps), 2, fontHeight + 2, 16777215 | i);
    }
}
