package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilMixin {
        @Inject(method = "onUse", at=@At("HEAD"))
        private void tpAnvilAbovePlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

                if (!world.isClient()) {
                        if(!player.isSneaky()) {
                                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                BlockPos playerPos = player.getBlockPos();
                                for (int i = 0; i < 10; ++i) {
                                        if (!world.getBlockState(playerPos.up(i)).isAir()) {
                                                world.setBlockState(playerPos.up(i), Blocks.AIR.getDefaultState());
                                        }
                                }
                                world.setBlockState(playerPos.up(10), state);
                        }
                }
        }
}
