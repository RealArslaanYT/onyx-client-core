package com.onyxclient.onyxclientcore;

import com.onyxclient.onyxclientcore.mods.Mod;
import com.onyxclient.onyxclientcore.mods.ModManager;
import com.onyxclient.onyxclientcore.mods.impl.OldCombat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OnyxClientCoreClient implements ClientModInitializer {
    public static KeyBinding modMenuKeyBind;
    public static OldCombat oldCombatMod = new OldCombat();

    @Override
    public void onInitializeClient() {
        // Keybindings
        modMenuKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.onyxclient.openModMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.onyxclient"
        ));

        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modMenuKeyBind.wasPressed()) {
                client.setScreen(new CreditsScreen(true, null));
            }
        });

        // Mod registration
        // TODO: Register mods here
        ModManager.getInstance().register(oldCombatMod);
        //ModManager.getInstance().toggleMod(oldCombatMod);

        // Register mod tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ModManager.getInstance().update();
        });
    }
}
