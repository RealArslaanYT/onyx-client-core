package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.gui.CustomMainMenuScreen;
import com.onyxclient.onyxclientcore.OnyxClientCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        MinecraftClient.getInstance().setScreen(new CustomMainMenuScreen());
        ci.cancel(); // Cancel the original TitleScreen init
        OnyxClientCore.LOGGER.info("TitleScreenMixin - Set screen to CustomMainMenuScreen and canceled TitleScreen init");
    }
}
