package com.github.zebus01.bullshitax3000.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class chestEntityMixin extends BlockEntity {


    public chestEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow
    public static void copyInventory(ChestBlockEntity from, ChestBlockEntity to) {
    }

    @Inject(method = "onOpen", at = @At("HEAD"))
    private void tpChestOnRandomPositon(PlayerEntity player, CallbackInfo ci){
        if (!player.world.isClient()) {
            if (!player.isSneaking()) {
                int x = (int) (Math.random() * 10 - 5);
                int z = (int) (Math.random() * 10 - 5);
                int y = (int) (Math.random() * 10 - 5);
                while(!player.world.getBlockState(player.getBlockPos().add(x, y, z)).isAir()){
                    x = (int) (Math.random() * 10 - 5);
                    y = (int) (Math.random() * 10 - 5);
                    z = (int) (Math.random() * 10 - 5);
                }
                player.world.setBlockState(player.getBlockPos().add(x, y, z), Blocks.CHEST.getDefaultState());
                copyInventory((ChestBlockEntity) (Object) this, (ChestBlockEntity) player.world.getBlockEntity(player.getBlockPos().add(x, y, z)));
                player.world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                System.out.println("Chest moved to " + player.getBlockPos().add(x, y, z));

            }
        }
    }

}
