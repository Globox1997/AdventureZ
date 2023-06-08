package net.adventurez.init;

import net.adventurez.effect.BlackstonedHeartEffect;
import net.adventurez.effect.FameEffect;
import net.adventurez.effect.WitheringEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EffectInit {
    public final static StatusEffect WITHERING = new WitheringEffect(StatusEffectCategory.HARMFUL, 657930);
    public final static StatusEffect FAME = new FameEffect(StatusEffectCategory.BENEFICIAL, 9442354);
    public final static StatusEffect BLACKSTONED_HEART = new BlackstonedHeartEffect(StatusEffectCategory.BENEFICIAL, 3481390);

    public static void init() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier("adventurez", "withering"), WITHERING);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("adventurez", "fame"), FAME);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("adventurez", "blackstoned_heart"), BLACKSTONED_HEART);
    }

}
