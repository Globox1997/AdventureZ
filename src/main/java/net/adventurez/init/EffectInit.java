package net.adventurez.init;

import net.adventurez.effect.FameEffect;
import net.adventurez.effect.WitheringEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectInit {
    public final static StatusEffect WITHERING = new WitheringEffect(StatusEffectCategory.HARMFUL, 657930);
    public final static StatusEffect FAME = new FameEffect(StatusEffectCategory.BENEFICIAL, 9442354);

    public static void init() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("adventurez", "withering"), WITHERING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("adventurez", "fame"), FAME);
    }

}
