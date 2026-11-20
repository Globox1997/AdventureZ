package net.adventurez.neoforge.world;

import com.mojang.serialization.MapCodec;

import net.adventurez.init.SpawnInit;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record SpawnBiomeModifier() implements BiomeModifier {

    public static final MapCodec<SpawnBiomeModifier> CODEC = MapCodec.unit(SpawnBiomeModifier::new);

    @Override
    public void modify(RegistryAccess registryAccess, Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            NeoForgeBiomeContext context = new NeoForgeBiomeContext(biome);
            SpawnInit.addSpawns((biomes, category, entityType, weight, minGroupSize, maxGroupSize) -> {
                if (biomes.test(context)) {
                    builder.getMobSpawnSettings().addSpawn(entityType, category, weight, minGroupSize == maxGroupSize ? ConstantInt.of(minGroupSize) : UniformInt.of(minGroupSize, maxGroupSize));
                }
            });
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    private record NeoForgeBiomeContext(Holder<Biome> biome) implements SpawnInit.BiomeContext {

        @Override
        public boolean is(ResourceKey<Biome> key) {
            return this.biome.is(key);
        }

        @Override
        public boolean is(TagKey<Biome> tag) {
            return this.biome.is(tag);
        }

        @Override
        public boolean isInNether() {
            return this.biome.is(BiomeTags.IS_NETHER);
        }

        @Override
        public boolean isInEnd() {
            return this.biome.is(BiomeTags.IS_END);
        }
    }

}
