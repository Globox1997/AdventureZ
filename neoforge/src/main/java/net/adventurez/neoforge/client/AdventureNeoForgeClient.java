package net.adventurez.neoforge.client;

import me.shedaniel.autoconfig.AutoConfigClient;
import net.adventurez.AdventureMain;
import net.adventurez.config.AdventureConfig;
import net.adventurez.init.KeybindInit;
import net.adventurez.init.RenderInit;
import net.adventurez.network.AdventureClientPacket;
import net.adventurez.network.packet.BlackstonedGolemPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = AdventureMain.MOD_ID, dist = Dist.CLIENT)
public class AdventureNeoForgeClient {

    public AdventureNeoForgeClient(IEventBus modBus, ModContainer container) {
        modBus.addListener(RegisterClientPayloadHandlersEvent.class, event -> {
            event.register(VelocityPacket.PACKET_ID, (payload, context) -> AdventureClientPacket.handleVelocity(payload));
            event.register(BlackstonedGolemPacket.PACKET_ID, (payload, context) -> AdventureClientPacket.handleBlackstonedGolem(payload));
        });

        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            event.registerCategory(KeybindInit.CATEGORY);
            KeybindInit.init(event::register);
        });
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, event -> KeybindInit.onClientTick(Minecraft.getInstance()));

        modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, event -> {
            RenderInit.registerEntityRenderers(new RenderInit.EntityRendererRegistrar() {
                @Override
                public <T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> provider) {
                    event.registerEntityRenderer(entityType, provider);
                }
            });
            RenderInit.registerBlockEntityRenderers(new RenderInit.BlockEntityRendererRegistrar() {
                @Override
                public <T extends BlockEntity, S extends BlockEntityRenderState> void register(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T, S> provider) {
                    event.registerBlockEntityRenderer(blockEntityType, provider);
                }
            });
        });
        modBus.addListener(EntityRenderersEvent.RegisterLayerDefinitions.class, event -> RenderInit.registerModelLayers(event::registerLayerDefinition));
        modBus.addListener(RegisterParticleProvidersEvent.class, event -> RenderInit.registerParticleProviders((particleType, provider) -> event.registerSpriteSet(particleType, provider::apply)));
        modBus.addListener(RegisterGuiLayersEvent.class, event -> event.registerAboveAll(RenderInit.HUD_ID, RenderInit::renderHud));

        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> AutoConfigClient.getConfigScreen(AdventureConfig.class, parent).get());
    }

}
