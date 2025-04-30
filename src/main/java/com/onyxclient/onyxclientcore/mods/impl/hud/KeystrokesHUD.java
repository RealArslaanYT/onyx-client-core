package com.onyxclient.onyxclientcore.mods.impl.hud;

import com.onyxclient.onyxclientcore.mods.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class KeystrokesHUD extends Mod {
    public KeystrokesHUD() {
        this.name = "Keystrokes HUD";
        this.description = "Displays your keystrokes!";
        this.setEnabled(false);
    }

    private boolean hudEventRegistered = false;

    private boolean forwardPressed = false; // W
    private boolean leftPressed = false; // A
    private boolean backPressed = false; // S
    private boolean rightPressed = false; // D
    private boolean lmbPressed = false; // Left Mouse Button
    private boolean rmbPressed = false; // Right Mouse Button
    private boolean jumpPressed = false; // Space

    @Override
    public void init() {
        if (hudEventRegistered) return;
        HudRenderCallback.EVENT.register(this::onRenderHud);
        hudEventRegistered = true;
    }

    private void onRenderHud(DrawContext context, RenderTickCounter renderTickCounter) {
        if (!isEnabled()) return;
        int width = context.getScaledWindowWidth();
        MinecraftClient client = MinecraftClient.getInstance();
        drawKeySquare(context, width - 56, 2, 25, 25, forwardPressed, client.options.forwardKey, false);
        drawKeySquare(context, width - 29, 29, 25, 25, rightPressed, client.options.rightKey, false);
        drawKeySquare(context, width - 56, 29, 25, 25, backPressed, client.options.backKey, false);
        drawKeySquare(context, width - 83, 29, 25, 25, leftPressed, client.options.leftKey, false);

        drawKeySquare(context, width - 83, 56, 38, 20, lmbPressed, null, true); // LMB
        drawKeySquare(context, width - 42, 56, 38, 20, rmbPressed, null, false); // RMB
        drawKeySquare(context, width - 83, 78, 81, 20, jumpPressed, client.options.jumpKey, false);
    }

    private void drawKeySquare(DrawContext context, int x, int y, int width, int height, boolean pressed, KeyBinding keyBinding, boolean isLMB) {
        float alpha = 0.37F;
        float textAlpha = 1.0F;
        int i = MathHelper.ceil(alpha * 255.0F) << 24;
        int iText = MathHelper.ceil(textAlpha * 255.0F) << 24;
        int color = pressed ? 0x00B500 : 0xFFFFFF;
        context.fill(x, y, x + width, y + height, color | i);

        if (keyBinding != null) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String keyText = keyBinding.getBoundKeyLocalizedText().getString();
            int textWidth = textRenderer.getWidth(keyText);
            int textHeight = textRenderer.fontHeight;
            int textX = (x + width / 2) - (textWidth / 2);
            int textY = (y + height / 2) - (textHeight / 2);
            context.drawTextWithShadow(textRenderer, Text.of(keyText), textX, textY, 16777215 | iText);
        } else {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String mouseButtonText = isLMB ? "LMB" : "RMB";
            int textWidth = textRenderer.getWidth(mouseButtonText);
            int textHeight = textRenderer.fontHeight;
            int textX = (x + width / 2) - (textWidth / 2);
            int textY = (y + height / 2) - (textHeight / 2);
            context.drawTextWithShadow(textRenderer, Text.of(mouseButtonText), textX, textY, 16777215 | iText);
        }
    }

    public void update() {
        MinecraftClient client = MinecraftClient.getInstance();
        forwardPressed = client.options.forwardKey.isPressed();
        leftPressed = client.options.leftKey.isPressed();
        backPressed = client.options.backKey.isPressed();
        rightPressed = client.options.rightKey.isPressed();

        // Track the mouse buttons
        lmbPressed = client.options.attackKey.isPressed();
        rmbPressed = client.options.useKey.isPressed();
        jumpPressed = client.options.jumpKey.isPressed();
    }
}
