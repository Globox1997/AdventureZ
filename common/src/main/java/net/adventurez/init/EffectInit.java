package net.adventurez.init;

import java.util.function.BiConsumer;

import net.adventurez.AdventureMain;
import net.adventurez.effect.BlackstonedHeartEffect;
import net.adventurez.effect.FameEffect;
import net.adventurez.effect.WitheringEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectInit {

    public static Holder<MobEffect> WITHERING;
    public static Holder<MobEffect> FAME;
    public static Holder<MobEffect> BLACKSTONED_HEART;

    public static void register(BiConsumer<Identifier, MobEffect> registrar) {
        WITHERING = register(registrar, "withering", new WitheringEffect(MobEffectCategory.HARMFUL, 657930));
        FAME = register(registrar, "fame", new FameEffect(MobEffectCategory.BENEFICIAL, 9442354));
        BLACKSTONED_HEART = register(registrar, "blackstoned_heart", new BlackstonedHeartEffect(MobEffectCategory.BENEFICIAL, 3481390));
    }

    private static Holder<MobEffect> register(BiConsumer<Identifier, MobEffect> registrar, String id, MobEffect statusEffect) {
        registrar.accept(AdventureMain.identifierOf(id), statusEffect);
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(statusEffect);
    }

}
