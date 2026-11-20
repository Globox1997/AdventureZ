package net.adventurez.neoforge.world;

import com.mojang.serialization.MapCodec;

import net.adventurez.init.SpawnInit;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.common.world.ModifiableStructureInfo;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.common.world.StructureSettingsBuilder;

public record FortressSpawnStructureModifier() implements StructureModifier {

    public static final MapCodec<FortressSpawnStructureModifier> CODEC = MapCodec.unit(FortressSpawnStructureModifier::new);

    @Override
    public void modify(RegistryAccess registryAccess, Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (phase == Phase.ADD && structure.is(BuiltinStructures.FORTRESS)) {
            StructureSettingsBuilder.StructureSpawnOverrideBuilder monsterSpawns = builder.getStructureSettings().getSpawnOverrides(MobCategory.MONSTER);
            if (monsterSpawns != null) {
                SpawnInit.addFortressSpawns(monsterSpawns::addSpawn);
            }
        }
    }

    @Override
    public MapCodec<? extends StructureModifier> codec() {
        return CODEC;
    }

}
