package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EffectInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;

public class BlackstoneGolemHeart extends Item {

    public BlackstoneGolemHeart(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.blackstone_golem_heart.tooltip"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if ((int) world.getGameTime() % 100 == 0) {
            world.playSound(null, entity.blockPosition(), SoundInit.HEART_BEAT_EVENT, SoundSource.AMBIENT, 1F, 1F);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.isShiftKeyDown()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.BLACKSTONE_GOLEM_AWAKENS_EVENT, SoundSource.PLAYERS, 1.4F, 1.0F);
            if (!world.isClientSide()) {
                user.addEffect(new MobEffectInstance(EffectInit.BLACKSTONED_HEART, ConfigInit.CONFIG.stoned_heart_duration, ConfigInit.CONFIG.stoned_heart_amplifier, false, false, true));
                itemStack.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return InteractionResultHolder.pass(itemStack);
        }
    }
}