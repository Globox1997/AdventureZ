package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

public class EventInit {

    public static void init() {
        ServerPlayerEvents.AFTER_RESPAWN.register((ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) -> {
            if (alive && oldPlayer.hasStatusEffect(EffectInit.FAME)) {
                newPlayer.addStatusEffect(new StatusEffectInstance(EffectInit.FAME, oldPlayer.getStatusEffect(EffectInit.FAME).getDuration(), 0, false, false, true));
            }
        });
        if (FabricLoader.getInstance().isModLoaded("tooltiprareness")) {
            ResourceManagerHelper.registerBuiltinResourcePack(AdventureMain.identifierOf("tooltiprareness_adventurez"), FabricLoader.getInstance().getModContainer("adventurez").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
    }

}
