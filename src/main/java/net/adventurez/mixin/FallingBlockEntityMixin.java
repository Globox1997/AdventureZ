package net.adventurez.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import net.voidz.init.BlockInit;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {

    @Shadow
    private BlockState block;

    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/FallingBlockEntity;destroyedOnLanding:Z"))
    private void tickMixin(CallbackInfo info) {
        Block block = this.block.getBlock();
        if (block == BlockInit.VOID_BLOCK) {
            if (!this.world.isClient) {
                this.world.playSound(null, this.getBlockPos(), SoundInit.ROCK_IMPACT_EVENT, SoundCategory.BLOCKS, 0.7F,
                        0.5F + (this.world.random.nextFloat() / 2.0F));

            }
        }

    }
}
