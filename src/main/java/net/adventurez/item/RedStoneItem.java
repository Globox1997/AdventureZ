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

import net.adventurez.entity.RedStoneEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class RedStoneItem extends Item {

  private final Supplier<EntityType<RedStoneEntity>> typeSupplier;
  private EntityType<RedStoneEntity> cachedType = null;

  public RedStoneItem(Settings settings, Supplier<EntityType<RedStoneEntity>> typeSupplier) {
    super(settings);
    this.typeSupplier = typeSupplier;
  }

  public EntityType<RedStoneEntity> getType() {
    if (cachedType == null) {
      cachedType = typeSupplier.get();
    }
    return cachedType;
  }

  @Override
  public boolean hasEnchantmentGlint(ItemStack stack) {
    return true;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.red_stone_item.tooltip"));
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW,
        SoundCategory.PLAYERS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
    if (!world.isClient) {
      RedStoneEntity eggEntity = new RedStoneEntity(world, user);
      eggEntity.setItem(itemStack);
      eggEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
      world.spawnEntity(eggEntity);
    }
    if (!user.abilities.creativeMode) {
      itemStack.decrement(1);
    }

    return TypedActionResult.method_29237(itemStack, world.isClient());
  }
}