package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"))
    private void explodingItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.isOf(Items.DIRT) || stack.isOf(Items.COBBLESTONE)) {
            //this.world.createExplosion(this, this.getX(), getY(), getZ(), stack.getCount() / 4.0f, World.ExplosionSourceType.TNT);
            System.out.println("client ? " + this.world.isClient());
            BlockPos.iterateRandomly(this.random, (stack.getCount() / 4) + 1, this.getBlockPos(), stack.getCount()/4).forEach((BlockPos blockPos) ->
                    this.world.createExplosion(null, blockPos.getX(), this.getY(), blockPos.getZ(), stack.getCount() / 8.0f, World.ExplosionSourceType.TNT));
        }
    }
}
