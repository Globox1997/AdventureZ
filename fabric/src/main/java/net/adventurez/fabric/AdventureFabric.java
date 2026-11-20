package net.adventurez.fabric;

import java.util.function.BiConsumer;

import net.adventurez.AdventureMain;
import net.adventurez.init.BlockInit;
import net.adventurez.init.EffectInit;
import net.adventurez.init.EntityInit;
import net.adventurez.init.EventInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.LootInit;
import net.adventurez.init.ParticleInit;
import net.adventurez.init.SoundInit;
import net.adventurez.init.SpawnInit;
import net.adventurez.network.AdventureServerPacket;
import net.adventurez.network.packet.BlackstonedGolemPacket;
import net.adventurez.network.packet.DragonFireBreathPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;

public class AdventureFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AdventureMain.init();

        BlockInit.registerBlocks(registrar(BuiltInRegistries.BLOCK));
        EffectInit.register(registrar(BuiltInRegistries.MOB_EFFECT));
        EntityInit.registerEntityTypes(registrar(BuiltInRegistries.ENTITY_TYPE));
        ItemInit.registerDataComponents(registrar(BuiltInRegistries.DATA_COMPONENT_TYPE));
        ItemInit.registerItems(registrar(BuiltInRegistries.ITEM));
        BlockInit.registerItems(registrar(BuiltInRegistries.ITEM));
        ItemInit.registerCreativeModeTabs(registrar(BuiltInRegistries.CREATIVE_MODE_TAB));
        BlockInit.registerBlockEntityTypes(registrar(BuiltInRegistries.BLOCK_ENTITY_TYPE));
        ParticleInit.register(registrar(BuiltInRegistries.PARTICLE_TYPE));
        SoundInit.register(registrar(BuiltInRegistries.SOUND_EVENT));

        EntityInit.registerAttributes(FabricDefaultAttributeRegistry::register);
        SpawnInit.registerSpawnPlacements(new SpawnInit.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
                SpawnPlacements.register(entityType, placementType, heightmapType, predicate);
            }
        });
        SpawnInit.addSpawns((biomes, category, entityType, weight, minGroupSize, maxGroupSize) -> BiomeModifications
                .addSpawn(context -> biomes.test(new FabricBiomeContext(context)), category, entityType, weight, minGroupSize, maxGroupSize));

        PayloadTypeRegistry.serverboundPlay().register(DragonFireBreathPacket.PACKET_ID, DragonFireBreathPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(VelocityPacket.PACKET_ID, VelocityPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(BlackstonedGolemPacket.PACKET_ID, BlackstonedGolemPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DragonFireBreathPacket.PACKET_ID, (payload, context) -> context.server().execute(() -> AdventureServerPacket.handleDragonFireBreath(context.player())));

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> LootInit.modifyLootTable(key, tableBuilder::pool));

        ServerPlayerEvents.AFTER_RESPAWN.register(EventInit::onPlayerRespawn);
        if (FabricLoader.getInstance().isModLoaded("tooltiprareness")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EventInit.TOOLTIP_RARENESS_PACK, FabricLoader.getInstance().getModContainer(AdventureMain.MOD_ID).orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
    }

    private static <T> BiConsumer<Identifier, T> registrar(Registry<? super T> registry) {
        return (id, value) -> Registry.register(registry, id, value);
    }

    private record FabricBiomeContext(BiomeSelectionContext context) implements SpawnInit.BiomeContext {

        @Override
        public boolean is(ResourceKey<Biome> biome) {
            return this.context.getBiomeKey().equals(biome);
        }

        @Override
        public boolean is(TagKey<Biome> tag) {
            return this.context.hasTag(tag);
        }

        @Override
        public boolean isInNether() {
            return this.context.canGenerateIn(LevelStem.NETHER);
        }

        @Override
        public boolean isInEnd() {
            return this.context.canGenerateIn(LevelStem.END);
        }
    }
}
