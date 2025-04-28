package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.FreelookCameraAccess;
import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements FreelookCameraAccess {
    @Unique
    private float freelookYaw;
    @Unique
    private float freelookPitch;

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void onChangeLookDirection(double deltaX, double deltaY, CallbackInfo ci) {
        // noinspection ConstantValue
        if (OnyxClientCoreClient.freelookMod.isFreelooking && ((Object) this instanceof ClientPlayerEntity)) {
            this.freelookYaw += (float) (deltaX * 0.15);
            this.freelookPitch = MathHelper.clamp(this.freelookPitch + (float) (deltaY * 0.15), -90.0F, 90.0F);
            ci.cancel();
        }
    }

    @Override
    public float getFreelookYaw() {
        return freelookYaw;
    }

    @Override
    public float getFreelookPitch() {
        return freelookPitch;
    }

    @Override
    public void setFreelookYaw(float yaw) {
        this.freelookYaw = yaw;
    }

    @Override
    public void setFreelookPitch(float pitch) {
        this.freelookPitch = pitch;
    }
}

