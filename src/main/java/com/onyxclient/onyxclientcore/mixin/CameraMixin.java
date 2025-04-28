package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.FreelookCameraAccess;
import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Inject(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void onUpdateCamera(BlockView blockView, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (OnyxClientCoreClient.freelookMod.isFreelooking && entity instanceof FreelookCameraAccess) {
            FreelookCameraAccess access = (FreelookCameraAccess) entity;
            this.setRotation(access.getFreelookYaw(), access.getFreelookPitch());
        }
    }
}
