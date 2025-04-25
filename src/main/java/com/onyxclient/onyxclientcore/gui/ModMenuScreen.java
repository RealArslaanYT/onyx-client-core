package com.onyxclient.onyxclientcore.gui;

import com.onyxclient.onyxclientcore.mods.Mod;
import com.onyxclient.onyxclientcore.mods.ModManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModMenuScreen extends Screen {
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 200;
    public ModMenuScreen() {
        super(Text.literal("Onyx Client | Mod Menu"));
    }

    @Override
    protected void init() {
        ModManager modManager = ModManager.getInstance();

        List<Mod> mods = modManager.getMods();
        List<ButtonWidget> buttons = new ArrayList<ButtonWidget>();

        int yOffset = 20;
        for (Mod mod : mods) {
            String modString = "";
            if (mod.isEnabled()) modString = "* ";
            modString = modString + mod.getName();
            ButtonWidget modButton = ButtonWidget.builder(Text.literal(modString), button -> {
                        modManager.toggleMod(mod);
                        this.clearChildren();
                        this.init();
                    })
                    .dimensions(width / 2 - BUTTON_WIDTH / 2, yOffset, BUTTON_WIDTH, BUTTON_HEIGHT)
                    .tooltip(Tooltip.of(Text.literal(mod.getDescription())))
                    .build();

            yOffset = yOffset + BUTTON_HEIGHT + 2;
            buttons.add(modButton);
        }

        for (ButtonWidget button : buttons) {
            addDrawableChild(button);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
