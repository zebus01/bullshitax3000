package com.github.zebus01.bullshitax3000;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;

public class Bullshitax3000 implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // Retrieve the player's main hand item
                ItemStack mainHandItem = player.getMainHandStack();

                // Check if the item in the main hand matches a specific item (e.g., diamond sword)
                if (mainHandItem.getItem() == Items.BLAZE_ROD) {
                    // Apply the desired effect to the player (e.g., Resistance for 10 seconds)

                    AreaEffectCloudEntity AreaEffectCloudEntity = new AreaEffectCloudEntity(player.getWorld(), player.getX(), player.getY(), player.getZ());
                    AreaEffectCloudEntity.setRadius(5.0F);
                    AreaEffectCloudEntity.setDuration(player.getMainHandStack().getCount());
                    AreaEffectCloudEntity.setParticleType(ParticleTypes.ELECTRIC_SPARK);
                    player.getWorld().spawnEntity(AreaEffectCloudEntity);
                }
            }
        });
    }
}

