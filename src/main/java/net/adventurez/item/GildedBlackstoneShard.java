package net.adventurez.item;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.adventurez.entity.nonliving.GildedBlackstoneShardEntity;
import net.adventurez.init.ConfigInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class GildedBlackstoneShard extends Item {

    private final Supplier<EntityType<GildedBlackstoneShardEntity>> typeSupplier;
    private EntityType<GildedBlackstoneShardEntity> cachedType = null;

    public GildedBlackstoneShard(Settings settings, Supplier<EntityType<GildedBlackstoneShardEntity>> typeSupplier) {
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
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Text.translatable("item.adventurez.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(Text.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Text.translatable("item.adventurez.gilded_blackstone_shard.tooltip"));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (ConfigInit.CONFIG.allow_gilded_blackstone_shard_throw && !user.isSneaking()) {
            world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
            if (!world.isClient()) {
                GildedBlackstoneShardEntity gildedStoneEntity = new GildedBlackstoneShardEntity(world, user.getX(), user.getY() + 1.6D, user.getZ());
                gildedStoneEntity.setItem(itemStack);
                gildedStoneEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.4F, 1.0F);
                world.spawnEntity(gildedStoneEntity);
            }
            if (!user.isCreative()) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }
}