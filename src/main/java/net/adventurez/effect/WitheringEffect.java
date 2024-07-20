package net.adventurez.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class WitheringEffect extends StatusEffect {

    private static final EntityAttributeModifier WITHERING = new EntityAttributeModifier(Identifier.of("adventurez:withering"), -0.15D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public WitheringEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().wither(), 0.5F);
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 80 == 0;
    }

    @Override
    public void onApplied(AttributeContainer attributeContainer, int amplifier) {
        super.onApplied(attributeContainer, amplifier);
        EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance((EntityAttributes.GENERIC_MOVEMENT_SPEED));
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(WITHERING);
            entityAttributeInstance.addPersistentModifier(WITHERING);
        }

    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(WITHERING);
        }

    }

}
