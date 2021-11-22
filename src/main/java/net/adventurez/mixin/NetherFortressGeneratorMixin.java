package net.adventurez.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.entity.BlazeGuardianEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(NetherFortressGenerator.BridgePlatform.class)
public class NetherFortressGeneratorMixin {

    @Inject(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void generateMixin(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos,
            CallbackInfo info, BlockState blockState, BlockState blockState2, BlockPos blockPos) {
        if (!world.isClient() && ConfigInit.CONFIG.allow_guardian_spawner_spawn) {
            BlazeGuardianEntity blazeGuardianEntity = (BlazeGuardianEntity) EntityInit.BLAZEGUARDIAN_ENTITY.create(world.toServerWorld());
            blazeGuardianEntity.initialize(world.toServerWorld(), world.getLocalDifficulty(blockPos), SpawnReason.STRUCTURE, null, null);
            int randomCheck = random.nextInt(3);
            blazeGuardianEntity.refreshPositionAndAngles(blockPos.north(random.nextInt(3) - 1).east(randomCheck - 1 == 0 ? -1 : randomCheck), random.nextFloat() * 360F, 0.0F);
            world.spawnEntity(blazeGuardianEntity);
        }
    }
}
