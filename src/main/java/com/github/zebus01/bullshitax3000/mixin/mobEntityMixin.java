package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class mobEntityMixin extends LivingEntity {
    protected mobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setTarget", at= @At("HEAD"))
    private void levelTarget(LivingEntity target, CallbackInfo ci) {
        if (!world.isClient()) {
            if (target!=null) {
                if (target.getY() > this.getY()) {
                    for (int i = 0; i < target.getY() - this.getY(); i++) {
                        world.setBlockState(target.getBlockPos().down(i), Blocks.AIR.getDefaultState());
                    }
                }
            }

        }
    }
}
