package net.adventurez.item;

import net.adventurez.entity.nonliving.GildedBlackstoneShardEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.function.Supplier;

public class GildedBlackstoneShard extends Item {

    private final Supplier<EntityType<GildedBlackstoneShardEntity>> typeSupplier;
    private EntityType<GildedBlackstoneShardEntity> cachedType = null;

    public GildedBlackstoneShard(Properties settings, Supplier<EntityType<GildedBlackstoneShardEntity>> typeSupplier) {
        super(settings);
        this.typeSupplier = typeSupplier;
    }

    public EntityType<GildedBlackstoneShardEntity> getType() {
        if (cachedType == null) {
            cachedType = typeSupplier.get();
        }
        return cachedType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.gilded_blackstone_shard.tooltip"));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (ConfigInit.CONFIG.allow_gilded_blackstone_shard_throw && !user.isShiftKeyDown()) {
            world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide()) {
                GildedBlackstoneShardEntity gildedStoneEntity = new GildedBlackstoneShardEntity(world, user.getX(), user.getY() + 1.6D, user.getZ());
                gildedStoneEntity.setItem(itemStack);
                gildedStoneEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.4F, 1.0F);
                world.addFreshEntity(gildedStoneEntity);
            }
            if (!user.isCreative()) {
                itemStack.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }
}