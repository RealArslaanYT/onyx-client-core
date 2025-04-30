package com.onyxclient.onyxclientcore;

import com.onyxclient.onyxclientcore.mods.ModManager;
import com.onyxclient.onyxclientcore.gui.ModMenuScreen;
import com.onyxclient.onyxclientcore.mods.impl.Freelook;
import com.onyxclient.onyxclientcore.mods.impl.OldCombat;
import com.onyxclient.onyxclientcore.mods.impl.hud.ArmorHUD;
import com.onyxclient.onyxclientcore.mods.impl.hud.CoordinatesHUD;
import com.onyxclient.onyxclientcore.mods.impl.hud.KeystrokesHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class OnyxClientCoreClient implements ClientModInitializer {
    public static KeyBinding modMenuKeyBind;
    public static OldCombat oldCombatMod = new OldCombat();
    public static CoordinatesHUD coordinatesHUDMod = new CoordinatesHUD();
    public static KeystrokesHUD keystrokesHUDMod = new KeystrokesHUD();
    public static Freelook freelookMod = new Freelook();
    public static KeyBinding freelookKeybinding;
    public static ArmorHUD armorHUDMod = new ArmorHUD();

    @Override
    public void onInitializeClient() {
        // Keybindings
        modMenuKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.onyxclient.openModMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.onyxclient"
        ));

        freelookKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.onyxclient.freelook",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "category.onyxclient"
        ));



        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modMenuKeyBind.wasPressed()) {
                client.setScreen(new ModMenuScreen());
            }
        });

        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, client) -> {
            String keyName = modMenuKeyBind.getBoundKeyLocalizedText().getString();
            assert client.player != null;
            client.player.sendMessage(Text.of("Press " + keyName + " to open Mod Menu"), true);
        });

        registerMods();
    }

    private void registerMods() {
        ModManager.getInstance().register(oldCombatMod);
        ModManager.getInstance().register(coordinatesHUDMod);
        ModManager.getInstance().register(keystrokesHUDMod);
        ModManager.getInstance().register(freelookMod);
        ModManager.getInstance().register(armorHUDMod);

        ModManager.getInstance().loadModStates();

        // Register ModManager events (tick/close)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ModManager.getInstance().update();
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ModManager.getInstance().close();
        });
    }
}
