package com.onyxclient.onyxclientcore.mixin;

import com.onyxclient.onyxclientcore.OnyxClientCoreClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Mixin to handle removing the hit delay if the "1.8-style Combat"/OldCombat mod is enabled.

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void onGetAttackCooldownProgress(CallbackInfoReturnable<Float> cir) {
        if (OnyxClientCoreClient.oldCombatMod.isEnabled()) cir.setReturnValue(1.0F);
    }
}
