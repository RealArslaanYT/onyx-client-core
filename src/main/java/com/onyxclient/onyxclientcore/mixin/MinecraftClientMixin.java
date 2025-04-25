package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.OnyxClientCore;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void onGetWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Onyx Client | Minecraft 1.21.5");
        OnyxClientCore.LOGGER.info("Returned custom window title");
    }
}
