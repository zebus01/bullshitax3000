package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(method = "attack", at = @At("HEAD"))
    private void knockBackWand(Entity target, CallbackInfo ci)
    {
        if (!world.isClient()) {
            if (this.getMainHandStack().isOf(Items.STICK)) {
                ((LivingEntity)target).takeKnockback(this.getMainHandStack().getCount(), MathHelper.sin(this.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(this.getYaw() * ((float)Math.PI / 180)));
            }
        }
    }
}
