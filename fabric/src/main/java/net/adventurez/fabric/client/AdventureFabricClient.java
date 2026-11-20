package net.adventurez.fabric.client;

import net.adventurez.init.KeybindInit;
import net.adventurez.init.RenderInit;
import net.adventurez.network.AdventureClientPacket;
import net.adventurez.network.packet.BlackstonedGolemPacket;
import net.adventurez.network.packet.VelocityPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Environment(EnvType.CLIENT)
public class AdventureFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(VelocityPacket.PACKET_ID, (payload, context) -> context.client().execute(() -> AdventureClientPacket.handleVelocity(payload)));
        ClientPlayNetworking.registerGlobalReceiver(BlackstonedGolemPacket.PACKET_ID, (payload, context) -> context.client().execute(() -> AdventureClientPacket.handleBlackstonedGolem(payload)));

        KeyMapping.Category.register(KeybindInit.CATEGORY.id());
        KeybindInit.init(KeyMappingHelper::registerKeyMapping);
        ClientTickEvents.END_CLIENT_TICK.register(KeybindInit::onClientTick);

        RenderInit.registerEntityRenderers(new RenderInit.EntityRendererRegistrar() {
            @Override
            public <T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> provider) {
                EntityRendererRegistry.register(entityType, provider);
            }
        });
        RenderInit.registerModelLayers((layer, definition) -> ModelLayerRegistry.registerModelLayer(layer, definition::get));
        RenderInit.registerBlockEntityRenderers(new RenderInit.BlockEntityRendererRegistrar() {
            @Override
            public <T extends BlockEntity, S extends BlockEntityRenderState> void register(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T, S> provider) {
                BlockEntityRenderers.register(blockEntityType, provider);
            }
        });
        RenderInit.registerParticleProviders((particleType, provider) -> ParticleProviderRegistry.getInstance().register(particleType, provider::apply));
        HudElementRegistry.addLast(RenderInit.HUD_ID, RenderInit::renderHud);
    }

}
