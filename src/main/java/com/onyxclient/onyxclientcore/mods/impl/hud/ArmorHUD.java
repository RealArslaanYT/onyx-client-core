package com.onyxclient.onyxclientcore.mods.impl.hud;

import com.onyxclient.onyxclientcore.OnyxClientCore;
import com.onyxclient.onyxclientcore.mods.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

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

    private void onRenderHud(DrawContext context, RenderTickCounter renderTickCounter) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        MinecraftClient client = MinecraftClient.getInstance();
        if (player == null) return;
        if (!this.isEnabled()) return;
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

        Identifier helmetId = Registries.ITEM.getId(helmet.getItem());
        Identifier chestplateId = Registries.ITEM.getId(chestplate.getItem());
        Identifier leggingsId = Registries.ITEM.getId(leggings.getItem());
        Identifier bootsId = Registries.ITEM.getId(boots.getItem());

        boolean renderHelmet = true;
        boolean renderChestplate = true;
        boolean renderLeggings = true;
        boolean renderBoots = true;

        if (helmetId.toString().equals("minecraft:air")) {
            renderHelmet = false;
        }
        if (chestplateId.toString().equals("minecraft:air")) {
            renderChestplate = false;
        }
        if (leggingsId.toString().equals("minecraft:air")) {
            renderLeggings = false;
        }
        if (bootsId.toString().equals("minecraft:air")) {
            renderBoots = false;
        }

        if (renderHelmet) {
            String[] parts = helmetId.toString().split(":");
            String namespace = parts[0];
            String itemName = parts[1];

            String texturePath = "textures/item/" + itemName + ".png";

            Identifier helmetTextureId = Identifier.of(namespace, texturePath);

            float alpha = 1.0F;
            int color = ColorHelper.getWhite(alpha);
            int width = context.getScaledWindowWidth();
            int height = context.getScaledWindowHeight();
            context.drawTexture(RenderLayer::getGuiTexturedOverlay, helmetTextureId, 2, height - 72, 0, 0, 16, 16, 16, 16, color);
        }

    }
}
