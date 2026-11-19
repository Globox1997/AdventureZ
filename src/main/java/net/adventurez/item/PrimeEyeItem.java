package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;

public class PrimeEyeItem extends Item {
    public PrimeEyeItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.prime_eye.tooltip"));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.getDamageValue() <= itemStack.getMaxDamage() - 1) {
            world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            user.getCooldowns().addCooldown(this, 20);
            if (!world.isClientSide()) {
                ThrownEnderpearl enderPearlEntity = new ThrownEnderpearl(world, user);
                enderPearlEntity.setItem(itemStack);
                enderPearlEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                world.addFreshEntity(enderPearlEntity);
                itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            }

            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else
            return InteractionResultHolder.pass(itemStack);
    }

}
