package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.adventurez.block.entity.DragonEggEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin implements BlockEntityProvider {

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new DragonEggEntity();
    }

}