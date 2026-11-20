package net.adventurez.item;

import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class BlackstoneGolemArm extends Item {

    public BlackstoneGolemArm(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("item.adventurez.blackstone_golem_arm.tooltip"));
            } else {
                tooltip.accept(Component.translatable("item.adventurez.moreinfo.tooltip"));
            }
        }
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player playerEntity) {
            int stoneCounter = this.getUseDuration(stack, user) - remainingUseTicks;
            if (stoneCounter >= 30) {
                stack.remove(ItemInit.LAVA_LIGHT);
                if (!world.isClientSide()) {
                    float strength = getStoneStrength(stoneCounter);
                    stack.hurtAndBreak(1, playerEntity, user.getUsedItemHand().asEquipmentSlot());
                    ThrownRockEntity thrownRockEntity = new ThrownRockEntity(world, playerEntity);
                    thrownRockEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, strength * 1.2F, 1.0F);
                    world.addFreshEntity(thrownRockEntity);
                    world.playSound((Player) null, thrownRockEntity, SoundInit.ROCK_THROW_EVENT, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
            return InteractionResult.FAIL;
        } else {
            user.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (slot == EquipmentSlot.MAINHAND) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 9, 0, false, false, false));
        }
        boolean usingThisStack = player.getItemBySlot(player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND) == stack;
        if (player.getUseItemRemainingTicks() < 71970 && player.getUseItemRemainingTicks() != 0 && usingThisStack) {
            if (!Boolean.TRUE.equals(stack.get(ItemInit.LAVA_LIGHT))) {
                stack.set(ItemInit.LAVA_LIGHT, true);
            }
        }
        if (Boolean.TRUE.equals(stack.get(ItemInit.LAVA_LIGHT)) && !usingThisStack) {
            stack.remove(ItemInit.LAVA_LIGHT);
        }
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3 vec3d_1 = attacker.getViewVector(1.0F);
        double x_vector = vec3d_1.x / 2D;
        double z_vector = vec3d_1.z / 2D;
        stack.hurtAndBreak(1, attacker, attacker.getUsedItemHand().asEquipmentSlot());
        target.push(x_vector, 0.45D, z_vector);
    }

    public static float getStoneStrength(int useTicks) {
        float f = (float) useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }
}
