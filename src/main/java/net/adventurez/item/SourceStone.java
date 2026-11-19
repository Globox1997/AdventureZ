package net.adventurez.item;

import net.adventurez.entity.nonliving.VoidBulletEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.SoundInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;

public class SourceStone extends Item {

    public SourceStone(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            tooltip.add(Component.translatable("item.adventurez.moreinfo.tooltip"));
            if (Screen.hasShiftDown()) {
                tooltip.remove(Component.translatable("item.adventurez.moreinfo.tooltip"));
                tooltip.add(Component.translatable("item.adventurez.source_stone.tooltip"));
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player playerEntity) {
            if (!world.isClientSide()) {
                if (ConfigInit.CONFIG.allow_source_stone_tp && playerEntity.isShiftKeyDown()) {
                    HitResult hitResult = playerEntity.pick(8D, 1.0F, false);
                    if (hitResult != null) {
                        if (hitResult.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                            tryTeleport((ServerLevel) world, blockHitResult.getBlockPos(), blockHitResult.getDirection(), playerEntity);
                        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                            tryTeleport((ServerLevel) world, entityHitResult.getEntity().blockPosition(), entityHitResult.getEntity().getDirection(), playerEntity);
                        }
                    }
                } else {
                    Vec3 vec3d = playerEntity.getViewVector(1.0F);
                    VoidBulletEntity voidBulletEntity = new VoidBulletEntity(playerEntity, vec3d, world);
                    world.addFreshEntity(voidBulletEntity);
                    world.playSound((Player) null, voidBulletEntity, SoundInit.VOID_SHADOW_CAST_EVENT, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
            playerEntity.getCooldowns().addCooldown(stack.getItem(), 40);
        }
    }

    private void tryTeleport(ServerLevel world, BlockPos blockPos, Direction direction, Player playerEntity) {
        if (!world.getBlockState(blockPos).isAir()) {
            for (int i = 2; i < 6; i++) {
                BlockPos newBlockPos = new BlockPos(blockPos).relative(direction.getOpposite(), i);
                BlockPos upperBlockPos = newBlockPos.above();
                if (!world.getBlockState(newBlockPos).isRedstoneConductor(world, newBlockPos) && !world.getBlockState(upperBlockPos).isRedstoneConductor(world, upperBlockPos)) {
                    playerEntity
                            .changeDimension(new DimensionTransition(world, newBlockPos.getBottomCenter(), playerEntity.getDeltaMovement(), playerEntity.getYRot(), playerEntity.getXRot(), DimensionTransition.DO_NOTHING));

                    world.playSound((Player) null, playerEntity, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.success(itemStack);
    }

}
