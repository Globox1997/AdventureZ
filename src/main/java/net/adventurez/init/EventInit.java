package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class EventInit {

    public static void init() {
        ServerPlayerEvents.AFTER_RESPAWN.register((ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) -> {
            if (alive && oldPlayer.hasEffect(EffectInit.FAME)) {
                newPlayer.addEffect(new MobEffectInstance(EffectInit.FAME, oldPlayer.getEffect(EffectInit.FAME).getDuration(), 0, false, false, true));
            }
        });
        if (FabricLoader.getInstance().isModLoaded("tooltiprareness")) {
            ResourceManagerHelper.registerBuiltinResourcePack(AdventureMain.identifierOf("tooltiprareness_adventurez"), FabricLoader.getInstance().getModContainer("adventurez").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
    }

}
