package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class EventInit {

    public static final Identifier TOOLTIP_RARENESS_PACK = AdventureMain.identifierOf("tooltiprareness_adventurez");

    public static void onPlayerRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        if (alive && oldPlayer.hasEffect(EffectInit.FAME)) {
            newPlayer.addEffect(new MobEffectInstance(EffectInit.FAME, oldPlayer.getEffect(EffectInit.FAME).getDuration(), 0, false, false, true));
        }
    }

}
