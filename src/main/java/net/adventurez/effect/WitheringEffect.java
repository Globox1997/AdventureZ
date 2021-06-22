package net.adventurez.effect;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class WitheringEffect extends StatusEffect {
    private static final UUID WITHERING = UUID.fromString("7a10abbd-01cb-4a42-b125-f1e67b35df03");

    public WitheringEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.WITHER, 0.5F);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 80 == 0;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance((EntityAttributes.GENERIC_MOVEMENT_SPEED));
        if (entityAttributeInstance != null) {
            EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(this.getTranslationKey(), -0.15D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            entityAttributeInstance.removeModifier(entityAttributeModifier);
            entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(WITHERING, this.getTranslationKey() + " " + entityAttributeModifier.getValue(),
                    this.adjustModifierAmount(amplifier, entityAttributeModifier), entityAttributeModifier.getOperation()));
        }

    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(WITHERING);
        }

    }

}
