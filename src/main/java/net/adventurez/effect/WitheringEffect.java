package net.adventurez.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.server.level.ServerLevel;

public class WitheringEffect extends MobEffect {

    private static final AttributeModifier WITHERING = new AttributeModifier(Identifier.parse("adventurez:withering"), -0.15D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public WitheringEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        entity.hurtServer(level, entity.damageSources().wither(), 0.5F);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 80 == 0;
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeContainer, int amplifier) {
        super.addAttributeModifiers(attributeContainer, amplifier);
        AttributeInstance entityAttributeInstance = attributeContainer.getInstance((Attributes.MOVEMENT_SPEED));
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(WITHERING);
            entityAttributeInstance.addPermanentModifier(WITHERING);
        }

    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeContainer) {
        super.removeAttributeModifiers(attributeContainer);
        AttributeInstance entityAttributeInstance = attributeContainer.getInstance(Attributes.MOVEMENT_SPEED);
        if (entityAttributeInstance != null) {
            entityAttributeInstance.removeModifier(WITHERING);
        }

    }

}
