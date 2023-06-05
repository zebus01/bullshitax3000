package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class livingEntityMixin extends Entity {
    public livingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method="onDeath", at=@At("HEAD"))
    private void spawnEndCrystalOnEndermanDeathInEnd(DamageSource damageSource, CallbackInfo ci) {
        if(!this.world.isClient()) {
            if (this.getType() == EntityType.ENDERMAN) {
                if (this.world.getRegistryKey() == World.END) {
                    this.world.spawnEntity(new net.minecraft.entity.decoration.EndCrystalEntity(this.world, this.getX(), this.getY(), this.getZ()));
                }
            }
        }
    }
}
