package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TallPlantBlock.class)
public class tallPlantMixin {
    @Inject(method = "onBreak", at= @At("HEAD"))
    private void dropEmerald(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci)
    {
        if(!world.isClient()) {
            TallPlantBlock.dropStack(world, pos, new ItemStack(Items.EMERALD, 1));
        }
    }
}
