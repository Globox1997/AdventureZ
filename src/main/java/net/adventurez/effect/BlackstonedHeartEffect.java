package net.adventurez.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BlackstonedHeartEffect extends MobEffect {

    private static final AttributeModifier BLACKSTONED = new AttributeModifier(ResourceLocation.parse("adventurez:blackstoned"), 1.0D, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier BLACKSTONED_HEALTH = new AttributeModifier(ResourceLocation.parse("adventurez:blackstoned_health"), 4.0D, AttributeModifier.Operation.ADD_VALUE);

    public BlackstonedHeartEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeContainer, int amplifier) {
        super.addAttributeModifiers(attributeContainer, amplifier);
        AttributeInstance entityAttributeInstance = attributeContainer.getInstance((Attributes.ATTACK_DAMAGE));
        AttributeInstance otherEntityAttributeInstance = attributeContainer.getInstance((Attributes.MAX_HEALTH));
        if (entityAttributeInstance != null && otherEntityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(BLACKSTONED);
            entityAttributeInstance.addPermanentModifier(BLACKSTONED);

            otherEntityAttributeInstance.removeModifier(BLACKSTONED_HEALTH);
            otherEntityAttributeInstance.addPermanentModifier(BLACKSTONED_HEALTH);
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeContainer) {
        super.removeAttributeModifiers(attributeContainer);
        AttributeInstance entityAttributeInstance = attributeContainer.getInstance(Attributes.ATTACK_DAMAGE);
        AttributeInstance otherEntityAttributeInstance = attributeContainer.getInstance(Attributes.MAX_HEALTH);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(BLACKSTONED);
        }
        if (otherEntityAttributeInstance != null) {
            otherEntityAttributeInstance.removeModifier(BLACKSTONED_HEALTH);
        }
    }

    @Override
    public void onMobRemoved(LivingEntity entity, int amplifier, RemovalReason reason) {
        super.onMobRemoved(entity, amplifier, reason);
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

}
