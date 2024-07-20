package net.adventurez.init;

import net.adventurez.effect.BlackstonedHeartEffect;
import net.adventurez.effect.FameEffect;
import net.adventurez.effect.WitheringEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class EffectInit {

    public final static RegistryEntry<StatusEffect> WITHERING = register("adventurez:withering", new WitheringEffect(StatusEffectCategory.HARMFUL, 657930));
    public final static RegistryEntry<StatusEffect> FAME = register("adventurez:fame", new FameEffect(StatusEffectCategory.BENEFICIAL, 9442354));
    public final static RegistryEntry<StatusEffect> BLACKSTONED_HEART = register("adventurez:blackstoned_heart", new BlackstonedHeartEffect(StatusEffectCategory.BENEFICIAL, 3481390));

    public static void init() {
    }

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(id), statusEffect);
    }

}
