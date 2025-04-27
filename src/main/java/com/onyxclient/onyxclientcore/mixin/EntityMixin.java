package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void onChangeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        if (OnyxClientCoreClient.freelookMod.isEnabled()) {
            Entity self = (Entity) (Object) this;

            float sensitivity = 0.15F;
            float g = (float) cursorDeltaX * sensitivity;

            self.setYaw(self.getYaw() + g);
            ci.cancel();
        }
    }
}
