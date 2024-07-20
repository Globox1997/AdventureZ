package net.adventurez.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class BlackstonedHeartEffect extends StatusEffect {

    private static final EntityAttributeModifier BLACKSTONED = new EntityAttributeModifier(Identifier.of("adventurez:blackstoned"), 1.0D, EntityAttributeModifier.Operation.ADD_VALUE);
    private static final EntityAttributeModifier BLACKSTONED_HEALTH = new EntityAttributeModifier(Identifier.of("adventurez:blackstoned_health"), 4.0D, EntityAttributeModifier.Operation.ADD_VALUE);

    public BlackstonedHeartEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(AttributeContainer attributeContainer, int amplifier) {
        super.onApplied(attributeContainer, amplifier);
        EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance((EntityAttributes.GENERIC_ATTACK_DAMAGE));
        EntityAttributeInstance otherEntityAttributeInstance = attributeContainer.getCustomInstance((EntityAttributes.GENERIC_MAX_HEALTH));
        if (entityAttributeInstance != null && otherEntityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(BLACKSTONED);
            entityAttributeInstance.addPersistentModifier(BLACKSTONED);

            otherEntityAttributeInstance.removeModifier(BLACKSTONED_HEALTH);
            otherEntityAttributeInstance.addPersistentModifier(BLACKSTONED_HEALTH);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        EntityAttributeInstance otherEntityAttributeInstance = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(BLACKSTONED);
        }
        if (otherEntityAttributeInstance != null) {
            otherEntityAttributeInstance.removeModifier(BLACKSTONED_HEALTH);
        }
    }

    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, RemovalReason reason) {
        super.onEntityRemoval(entity, amplifier, reason);
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

}
