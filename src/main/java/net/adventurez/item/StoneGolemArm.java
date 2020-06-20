package net.adventurez.item;

import java.util.List;

import net.adventurez.entity.ThrownRockEntity;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class StoneGolemArm extends Item {
  int stoneCounter = 0;

  public StoneGolemArm(Settings settings) {
    super(settings);
    FabricModelPredicateProviderRegistry.register(new Identifier("phase"), (stack, world, entity) -> {
      if (stoneCounter >= 600 && stoneCounter <= 1200) {
        return 0.5F;
      }
      return 0F;
    });
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.adventurez.stone_golem_arm.tooltip"));
  }

  @Override
  public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    if (user instanceof PlayerEntity) {
      PlayerEntity playerEntity = (PlayerEntity) user;
      int i = this.getMaxUseTime(stack) - remainingUseTicks;
      int count = 0;
      if (count < 500) {
        System.out.println("MaxUseTime:" + i);
        count++;
      }

      if (i >= 10) {
        if (!world.isClient) {
          stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
          ThrownRockEntity tridentEntity = new ThrownRockEntity(world, playerEntity);
          tridentEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, 2.5F, 1.0F); // +
                                                                                                             // (float)
                                                                                                             // j * 0.5F

          world.spawnEntity(tridentEntity);
          world.playSoundFromEntity((PlayerEntity) null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW,
              SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
      }

    }

  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
      return TypedActionResult.fail(itemStack);
    } else {
      user.setCurrentHand(hand);
      return TypedActionResult.fail(itemStack);
    }
  }

  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    PlayerEntity player = (PlayerEntity) entity;
    StatusEffectInstance slowness = new StatusEffectInstance(StatusEffect.byRawId(2), 9, 0, false, false);
    if (selected && !world.isClient) {
      player.addStatusEffect(slowness);
      if (stoneCounter > 0) {
        stoneCounter--;
      }
    }
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.BOW;
  }

  @Override
  public int getMaxUseTime(ItemStack stack) {
    return 72000;
  }

}