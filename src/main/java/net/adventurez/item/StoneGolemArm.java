package net.adventurez.item;

import java.util.List;

import net.adventurez.entity.ThrownRockEntity;
import net.adventurez.init.SoundInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.nbt.CompoundTag;

public class StoneGolemArm extends Item {

    public StoneGolemArm(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("item.adventurez.stone_golem_arm.tooltip"));
        tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
            tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
            tooltip.add(new TranslatableText("item.adventurez.stone_golem_arm.tooltip2"));
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        CompoundTag tags = stack.getTag();
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            int stoneCounter;
            stoneCounter = this.getMaxUseTime(stack) - remainingUseTicks;
            if (stoneCounter >= 30) {
                tags.putBoolean("lavalight", false);
                if (!world.isClient) {
                    float strength = getStoneStrength(stoneCounter);
                    stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                    ThrownRockEntity rockEntity = new ThrownRockEntity(world, playerEntity);
                    rockEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, strength * 1.2F,
                            1.0F);
                    world.spawnEntity(rockEntity);
                    world.playSoundFromEntity((PlayerEntity) null, rockEntity, SoundInit.ROCK_THROW_EVENT,
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
            return TypedActionResult.success(itemStack);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        PlayerEntity player = (PlayerEntity) entity;
        CompoundTag tags = stack.getTag();
        StatusEffectInstance slowness = new StatusEffectInstance(StatusEffect.byRawId(2), 9, 0, false, false);
        if (selected && !world.isClient) {
            player.addStatusEffect(slowness);
        }

        if (world.isClient && tags != null) {
            if (player.getItemUseTimeLeft() < 71970 && player.getItemUseTimeLeft() != 0
                    && player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND
                            : EquipmentSlot.OFFHAND) == stack) {
                tags.putBoolean("lavalight", true);
            }

            if (tags.getBoolean("lavalight")
                    && player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND
                            : EquipmentSlot.OFFHAND) != stack) {
                tags.putBoolean("lavalight", false);
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == Items.NETHERITE_SCRAP;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3d vec3d_1 = attacker.getRotationVec(1.0F);
        double x_vector = vec3d_1.x / 2D;
        double z_vector = vec3d_1.z / 2D;
        stack.damage(1, attacker, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
        target.addVelocity(x_vector, 0.45D, z_vector);
        return true;
    }

    public static float getStoneStrength(int useTicks) {
        float f = (float) useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

}