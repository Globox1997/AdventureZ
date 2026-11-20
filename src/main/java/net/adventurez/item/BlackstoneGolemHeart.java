package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EffectInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class BlackstoneGolemHeart extends Item {

    public BlackstoneGolemHeart(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("item.adventurez.blackstone_golem_heart.tooltip"));
            } else {
                tooltip.accept(Component.translatable("item.adventurez.moreinfo.tooltip"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        if ((int) world.getGameTime() % 100 == 0) {
            world.playSound(null, entity.blockPosition(), SoundInit.HEART_BEAT_EVENT, SoundSource.AMBIENT, 1F, 1F);
        }
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.isShiftKeyDown()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.BLACKSTONE_GOLEM_AWAKENS_EVENT, SoundSource.PLAYERS, 1.4F, 1.0F);
            if (!world.isClientSide()) {
                user.addEffect(new MobEffectInstance(EffectInit.BLACKSTONED_HEART, ConfigInit.CONFIG.stoned_heart_duration, ConfigInit.CONFIG.stoned_heart_amplifier, false, false, true));
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

}
