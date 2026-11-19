package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.adventurez.effect.BlackstonedHeartEffect;
import net.adventurez.effect.FameEffect;
import net.adventurez.effect.WitheringEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectInit {

    public final static Holder<MobEffect> WITHERING = register("withering", new WitheringEffect(MobEffectCategory.HARMFUL, 657930));
    public final static Holder<MobEffect> FAME = register("fame", new FameEffect(MobEffectCategory.BENEFICIAL, 9442354));
    public final static Holder<MobEffect> BLACKSTONED_HEART = register("blackstoned_heart", new BlackstonedHeartEffect(MobEffectCategory.BENEFICIAL, 3481390));

    public static void init() {
    }

    private static Holder<MobEffect> register(String id, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, AdventureMain.identifierOf(id), statusEffect);
    }

}
