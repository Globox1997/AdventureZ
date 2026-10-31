package net.adventurez.item;

import net.adventurez.init.ConfigInit;
import net.adventurez.init.EffectInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class BlackstoneGolemHeart extends Item {

    public BlackstoneGolemHeart(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Text.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Text.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.blackstone_golem_heart.tooltip"));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if ((int) world.getTime() % 100 == 0) {
            world.playSound(null, entity.getBlockPos(), SoundInit.HEART_BEAT_EVENT, SoundCategory.AMBIENT, 1F, 1F);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.GOLEM_AWAKENS_EVENT, SoundCategory.PLAYERS, 1.4F, 1.0F);
            if (!world.isClient()) {
                user.addStatusEffect(new StatusEffectInstance(EffectInit.BLACKSTONED_HEART, ConfigInit.CONFIG.stoned_heart_duration, ConfigInit.CONFIG.stoned_heart_amplifier, false, false, true));
                itemStack.decrement(1);
            }
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.pass(itemStack);
        }
    }
}