package com.onyxclient.onyxclientcore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OnyxClient_Client implements ClientModInitializer {
    public static KeyBinding modMenuKeyBind;

    @Override
    public void onInitializeClient() {
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
    }
}
