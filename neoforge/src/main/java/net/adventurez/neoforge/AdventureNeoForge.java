package net.adventurez.neoforge;

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
import net.adventurez.neoforge.world.FortressSpawnStructureModifier;
import net.adventurez.neoforge.world.SpawnBiomeModifier;
import net.adventurez.network.AdventureServerPacket;
import net.adventurez.network.packet.BlackstonedGolemPacket;
import net.adventurez.network.packet.DragonFireBreathPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(AdventureMain.MOD_ID)
public class AdventureNeoForge {

    public AdventureNeoForge(IEventBus modBus) {
        AdventureMain.init();

        modBus.addListener(RegisterEvent.class, AdventureNeoForge::register);
        modBus.addListener(EntityAttributeCreationEvent.class, event -> EntityInit.registerAttributes((entityType, builder) -> event.put(entityType, builder.build())));
        modBus.addListener(RegisterSpawnPlacementsEvent.class, event -> SpawnInit.registerSpawnPlacements(new SpawnInit.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(entityType, placementType, heightmapType, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            }
        }));
        modBus.addListener(RegisterPayloadHandlersEvent.class, AdventureNeoForge::registerPayloads);
        modBus.addListener(AddPackFindersEvent.class, AdventureNeoForge::addPackFinders);

        NeoForge.EVENT_BUS.addListener(LootTableLoadEvent.class, event -> LootInit.modifyLootTable(event.getKey(), event.getTable()::addPool));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class, event -> {
            if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
                EventInit.onPlayerRespawn(oldPlayer, newPlayer, !event.isWasDeath());
            }
        });
    }

    private static void register(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> BlockInit.registerBlocks(helper::register));
        event.register(Registries.MOB_EFFECT, helper -> EffectInit.register(helper::register));
        event.register(Registries.ENTITY_TYPE, helper -> EntityInit.registerEntityTypes(helper::register));
        event.register(Registries.DATA_COMPONENT_TYPE, helper -> ItemInit.registerDataComponents(helper::register));
        event.register(Registries.ITEM, helper -> {
            ItemInit.registerItems(helper::register);
            BlockInit.registerItems(helper::register);
        });
        event.register(Registries.CREATIVE_MODE_TAB, helper -> ItemInit.registerCreativeModeTabs(helper::register));
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> BlockInit.registerBlockEntityTypes(helper::register));
        event.register(Registries.PARTICLE_TYPE, helper -> ParticleInit.register(helper::register));
        event.register(Registries.SOUND_EVENT, helper -> SoundInit.register(helper::register));
        event.register(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, helper -> helper.register(SpawnInit.BIOME_MODIFIER_ID, SpawnBiomeModifier.CODEC));
        event.register(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, helper -> helper.register(SpawnInit.STRUCTURE_MODIFIER_ID, FortressSpawnStructureModifier.CODEC));
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").optional();
        registrar.playToServer(DragonFireBreathPacket.PACKET_ID, DragonFireBreathPacket.PACKET_CODEC,
                (payload, context) -> AdventureServerPacket.handleDragonFireBreath((ServerPlayer) context.player()));
        registrar.playToClient(VelocityPacket.PACKET_ID, VelocityPacket.PACKET_CODEC);
        registrar.playToClient(BlackstonedGolemPacket.PACKET_ID, BlackstonedGolemPacket.PACKET_CODEC);
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && ModList.get().isLoaded("tooltiprareness")) {
            event.addPackFinders(AdventureMain.identifierOf("resourcepacks/" + EventInit.TOOLTIP_RARENESS_PACK.getPath()), PackType.SERVER_DATA,
                    Component.translatable("pack.adventurez.tooltiprareness_adventurez"), PackSource.BUILT_IN, true, Pack.Position.TOP);
        }
    }

}
