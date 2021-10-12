package net.adventurez.effect;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BlackstonedHeartEffect extends StatusEffect {

    private static final UUID BLACKSTONED = UUID.fromString("9b5ef1de-c002-4c01-9d8f-9203fcacc9a4");
    private static final UUID BLACKSTONED_HEALTH = UUID.fromString("dc01ece9-d9d7-45a0-b568-5fb8b7d73a23");

    public BlackstonedHeartEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance((EntityAttributes.GENERIC_ATTACK_DAMAGE));
        EntityAttributeInstance otherEntityAttributeInstance = attributes.getCustomInstance((EntityAttributes.GENERIC_MAX_HEALTH));
        if (entityAttributeInstance != null && otherEntityAttributeInstance != null) {
            EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(this.getTranslationKey(), 1.0D, EntityAttributeModifier.Operation.ADDITION);
            entityAttributeInstance.removeModifier(entityAttributeModifier);
            entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(BLACKSTONED, this.getTranslationKey() + " " + entityAttributeModifier.getValue(),
                    this.adjustModifierAmount(amplifier, entityAttributeModifier), entityAttributeModifier.getOperation()));

            EntityAttributeModifier otherEntityAttributeModifier = new EntityAttributeModifier(this.getTranslationKey(), 4.0D, EntityAttributeModifier.Operation.ADDITION);
            otherEntityAttributeInstance.removeModifier(otherEntityAttributeModifier);
            otherEntityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(BLACKSTONED_HEALTH, this.getTranslationKey() + " " + entityAttributeModifier.getValue(),
                    this.adjustModifierAmount(amplifier, otherEntityAttributeModifier), otherEntityAttributeModifier.getOperation()));
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        EntityAttributeInstance otherEntityAttributeInstance = attributes.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance != null)
            entityAttributeInstance.removeModifier(BLACKSTONED);
        if (otherEntityAttributeInstance != null)
            otherEntityAttributeInstance.removeModifier(BLACKSTONED_HEALTH);
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

}
