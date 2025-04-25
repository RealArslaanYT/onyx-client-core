package com.onyxclient.onyxclientcore.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CustomMainMenuScreen extends Screen {
    public CustomMainMenuScreen() {
        super(Text.literal("Onyx Client"));
    }

    private static final Identifier ONYX_LOGO = Identifier.of("onyx-client-core", "textures/gui/logo.png");
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
                .dimensions(width / 2 - 100, height / 2 - 35 + 30, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Play a world")))
                .build();

        multiplayerButton = ButtonWidget.builder(Text.literal("Multiplayer"), button -> {
                    client.setScreen(new MultiplayerScreen(this));
                })
                .dimensions(width / 2 - 100, height / 2 - 10 + 30, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Join a server")))
                .build();

        optionsButton = ButtonWidget.builder(Text.literal("Options"), button -> {
                    client.setScreen(new OptionsScreen(this, client.options));
                })
                .dimensions(width / 2 - 100, height / 2 + 25 + 30, 95, 20)
                .tooltip(Tooltip.of(Text.literal("Change settings")))
                .build();

        quitButton = ButtonWidget.builder(Text.literal("Quit"), button -> {
                    client.scheduleStop();
                })
                .dimensions(width / 2 + 5, height / 2 + 25 + 30, 95, 20)
                .tooltip(Tooltip.of(Text.literal("Quit the game")))
                .build();

        addDrawableChild(singleplayerButton);
        addDrawableChild(multiplayerButton);
        addDrawableChild(optionsButton);
        addDrawableChild(quitButton);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        float alpha = 1.0F;

        int logoWidth = 90;
        int logoHeight = 90;
        int x = this.width / 2 - logoWidth / 2;
        int y = 25;

        int color = ColorHelper.getWhite(alpha);
        context.drawTexture(RenderLayer::getGuiTexturedOverlay, ONYX_LOGO, x, y, 0.0F, 0.0F, logoWidth, logoHeight, logoWidth, logoHeight, color);

        int i = MathHelper.ceil(alpha * 255.0F) << 24;
        context.drawTextWithShadow(this.textRenderer, "Onyx Client 1.21.5", 2, this.height - 10, 16777215 | i);

        String copyrightText = "Copyright Mojang AB. Do not distribute!";
        int copyrightTextWidth = this.textRenderer.getWidth(Text.literal(copyrightText));
        int copyrightX = this.width - copyrightTextWidth - 2;
        context.drawTextWithShadow(this.textRenderer, copyrightText, copyrightX, this.height - 10, 16777215 | i);
    }
}
