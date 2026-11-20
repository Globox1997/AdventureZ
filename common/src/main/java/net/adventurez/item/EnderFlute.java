package net.adventurez.item;

import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class EnderFlute extends Item {

    public EnderFlute(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("item.adventurez.ender_flute.tooltip"));
            } else {
                tooltip.accept(Component.translatable("item.adventurez.moreinfo.tooltip"));
            }
        }
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.FLUTE_CALL_EVENT, SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.2F + 0.9F);
        if (!world.isClientSide()) {
            itemStack.hurtAndBreak(1, user, hand.asEquipmentSlot());
            List<EnderWhaleEntity> list = world.getEntitiesOfClass(EnderWhaleEntity.class, new AABB(user.blockPosition()).inflate(100D), EntitySelector.NO_SPECTATORS);
            if (!list.isEmpty()) {
                list.get(0).getMoveControl().setWantedPosition(user.getX(), user.getY(), user.getZ(), 1.0D);
            }
        }
        user.getCooldowns().addCooldown(itemStack, 40);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof ServerPlayer playerEntity) {
            float cooldownProgress = playerEntity.getCooldowns().getCooldownPercent(stack, 0.0F);
            if (cooldownProgress > 0.0F) {
                world.sendParticles(playerEntity, ParticleTypes.NOTE, false, false, (double) playerEntity.getX() + playerEntity.getForward().x() + world.getRandom().nextDouble() * 0.4D,
                        (double) playerEntity.getY() + playerEntity.getBbHeight() * 0.8D, (double) playerEntity.getZ() + playerEntity.getForward().z() + world.getRandom().nextDouble() * 0.4D, 0,
                        playerEntity.getForward().x() * world.getRandom().nextDouble(), 0.0D, 0.0D, 1.0D);
            }
        }
    }

}
