package com.onyxclient.onyxclientcore;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CustomMainMenuScreen extends Screen {
    public CustomMainMenuScreen() {
        super(Text.literal("Onyx Client"));
    }

    public ButtonWidget singleplayerButton;
    public ButtonWidget multiplayerButton;
    public ButtonWidget optionsButton;

    public ButtonWidget quitButton;

    @Override
    protected void init() {
        MinecraftClient client = MinecraftClient.getInstance();

        singleplayerButton = ButtonWidget.builder(Text.literal("Singleplayer"), button -> {
                    client.setScreen(new SelectWorldScreen(this));
                })
                .dimensions(width / 2 - 100, height / 2 - 35, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Play a world")))
                .build();

        multiplayerButton = ButtonWidget.builder(Text.literal("Multiplayer"), button -> {
                    client.setScreen(new MultiplayerScreen(this));
                })
                .dimensions(width / 2 - 100, height / 2 - 10, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Join a server")))
                .build();

        optionsButton = ButtonWidget.builder(Text.literal("Options"), button -> {
                    client.setScreen(new OptionsScreen(this, client.options));
                })
                .dimensions(width / 2 - 100, height / 2 + 25, 95, 20)
                .tooltip(Tooltip.of(Text.literal("Change settings")))
                .build();

        quitButton = ButtonWidget.builder(Text.literal("Quit"), button -> {
                    client.scheduleStop(); // Cleanly exits the game
                })
                .dimensions(width / 2 + 5, height / 2 + 25, 95, 20)
                .tooltip(Tooltip.of(Text.literal("Quit the game")))
                .build();

        addDrawableChild(singleplayerButton);
        addDrawableChild(multiplayerButton);
        addDrawableChild(optionsButton);
        addDrawableChild(quitButton);
    }

}
