package net.adventurez.item;

import java.util.List;

import net.adventurez.entity.nonliving.VoidBulletEntity;
import net.adventurez.init.SoundInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SourceStoneItem extends Item {

    public SourceStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
        tooltip.add(new TranslatableText("item.adventurez.moreinfo.tooltip"));
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
            tooltip.remove(new TranslatableText("item.adventurez.moreinfo.tooltip"));
            tooltip.add(new TranslatableText("item.adventurez.source_stone.tooltip"));
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            if (!world.isClient) {
                if (playerEntity.isSneaking()) {
                    HitResult hitResult = playerEntity.raycast(8D, 1.0F, false);
                    if (hitResult != null) {
                        if (hitResult.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                            tryTeleport(world, blockHitResult.getBlockPos(), blockHitResult.getSide(), playerEntity);
                        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                            tryTeleport(world, new BlockPos(entityHitResult.getPos()),
                                    entityHitResult.getEntity().getHorizontalFacing(), playerEntity);
                        }
                    }
                } else {
                    Vec3d vec3d = playerEntity.getRotationVec(1.0F);
                    VoidBulletEntity voidBulletEntity = new VoidBulletEntity(world, playerEntity, vec3d.x, vec3d.y,
                            vec3d.z);
                    world.spawnEntity(voidBulletEntity);
                    world.playSoundFromEntity((PlayerEntity) null, voidBulletEntity, SoundInit.SHADOW_CAST_EVENT,
                            SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
            playerEntity.getItemCooldownManager().set(stack.getItem(), 40);
        }
    }

    private void tryTeleport(World world, BlockPos blockPos, Direction direction, PlayerEntity playerEntity) {
        if (!world.getBlockState(blockPos).isAir()) {
            for (int i = 2; i < 6; i++) {
                BlockPos newBlockPos = new BlockPos(blockPos).offset(direction.getOpposite(), i);
                if (world.getBlockState(newBlockPos).isAir() && world.getBlockState(newBlockPos.up()).isAir()) {
                    playerEntity.teleport(newBlockPos.getX(), newBlockPos.getY(), newBlockPos.getZ());
                    world.playSoundFromEntity((PlayerEntity) null, playerEntity, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                            SoundCategory.PLAYERS, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack);
    }

}
