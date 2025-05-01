package com.onyxclient.onyxclientcore.mods.impl.hud;

import com.onyxclient.onyxclientcore.mods.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class ArmorHUD extends Mod {
    public ArmorHUD() {
        this.name = "Armor HUD";
        this.description = "Shows your armor and its durability in the HUD!";
        this.setEnabled(false);
    }
    
    private boolean hudEventRegistered = false;
    
    public void init() {
        if (hudEventRegistered) return;
        HudRenderCallback.EVENT.register(this::onRenderHud);
        hudEventRegistered = true;
    }

    private void onRenderHud(DrawContext context, RenderTickCounter tickCounter) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !this.isEnabled()) return;

        EquipmentSlot[] slots = {
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET
        };

        int height = context.getScaledWindowHeight();
        int width = context.getScaledWindowWidth();
        int baseX = width - 24;
        int baseY = height - 72;

        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = player.getEquippedStack(slots[i]);
            if (!stack.isEmpty()) {
                int x = baseX;
                int y = baseY + i * 18;

                int maxDurability = stack.getMaxDamage();
                int currentDurability = stack.getDamage();
                int durabilityPercentage = Math.round(((float) (maxDurability - currentDurability) / maxDurability) * 100);

                context.drawItem(stack, x, y);

                String durabilityText = durabilityPercentage + "%";
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                int textWidth = textRenderer.getWidth(durabilityText);
                float alpha = 1.0F;
                int alphaI = MathHelper.ceil(alpha * 255.0F) << 24;
                context.drawTextWithShadow(textRenderer, durabilityText, x - textWidth - 8, (y + 8) - (textRenderer.fontHeight / 2), 16777215 | alphaI);
            }
        }
    }
}
