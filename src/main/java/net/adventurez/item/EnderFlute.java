package net.adventurez.item;

import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class EnderFlute extends Item {

    public EnderFlute(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.ender_flute.tooltip"));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.FLUTE_CALL_EVENT, SoundSource.PLAYERS, 1.0F, world.random.nextFloat() * 0.2F + 0.9F);
        if (!world.isClientSide()) {
            itemStack.hurtAndBreak(1, user, LivingEntity.getSlotForHand(hand));
            List<EnderWhaleEntity> list = world.getEntitiesOfClass(EnderWhaleEntity.class, new AABB(user.blockPosition()).inflate(100D), EntitySelector.NO_SPECTATORS);
            if (!list.isEmpty()) {
                list.get(0).getMoveControl().setWantedPosition(user.getX(), user.getY(), user.getZ(), 1.0D);
            }
        }

        user.getCooldowns().addCooldown(itemStack.getItem(), 40);
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide() && entity instanceof Player) {
            Player playerEntity = (Player) entity;
            float cooldownProgress = playerEntity.getCooldowns().getCooldownPercent(stack.getItem(), 0.0F);
            if (cooldownProgress > 0.0F) {
                world.addParticle(ParticleTypes.NOTE, (double) playerEntity.getX() + playerEntity.getForward().x() + world.random.nextDouble() * 0.4D,
                        (double) playerEntity.getY() + playerEntity.getBbHeight() * 0.8D, (double) playerEntity.getZ() + playerEntity.getForward().z() + world.random.nextDouble() * 0.4D,
                        playerEntity.getForward().x() * world.random.nextDouble(), 0.0D, 0.0D);
            }
        }
    }
}
