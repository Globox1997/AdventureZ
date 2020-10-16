package net.adventurez.item;

import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.List;
import java.util.function.Supplier;

import net.adventurez.entity.GildedStoneEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class GildedStoneItem extends Item {

  private final Supplier<EntityType<GildedStoneEntity>> typeSupplier;
  private EntityType<GildedStoneEntity> cachedType = null;

  public GildedStoneItem(Settings settings, Supplier<EntityType<GildedStoneEntity>> typeSupplier) {
    super(settings);
    this.typeSupplier = typeSupplier;
  }

  public EntityType<GildedStoneEntity> getType() {
    if (cachedType == null) {
      cachedType = typeSupplier.get();
    }
    return cachedType;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.gilded_stone_item.tooltip"));
    tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
    if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
      tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
      tooltip.add(new TranslatableText("item.adventurez.gilded_stone_item.tooltip2"));
    }
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    if (!user.isSneaking()) {
      world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW,
          SoundCategory.PLAYERS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
      if (!world.isClient) {
        GildedStoneEntity gildedStoneEntity = new GildedStoneEntity(world, user.getX(), user.getY() + 1.6D,
            user.getZ());
        gildedStoneEntity.setItem(itemStack);
        gildedStoneEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.4F, 1.0F);
        world.spawnEntity(gildedStoneEntity);
      }
      if (!user.abilities.creativeMode) {
        itemStack.decrement(1);
      }

      return TypedActionResult.success(itemStack, world.isClient());
    }
    return TypedActionResult.pass(itemStack);
  }
}