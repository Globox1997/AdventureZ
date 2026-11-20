package net.adventurez.block.entity;

import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.ChestLidAnimatorAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.entity.ContainerUser;

public class ShadowChestEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {

    private final ChestLidController lidAnimator = new ChestLidController();
    private NonNullList<ItemStack> inventory;
    private ContainerOpenersCounter stateManager;

    public ShadowChestEntity(BlockPos pos, BlockState state) {
        super(BlockInit.SHADOW_CHEST_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
        this.stateManager = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(Level world, BlockPos pos, BlockState state) {
                world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundInit.OPEN_SHADOW_CHEST_EVENT, SoundSource.BLOCKS, 0.5F,
                        world.getRandom().nextFloat() * 0.1F + 0.9F);
            }

            @Override
            protected void onClose(Level world, BlockPos pos, BlockState state) {
                world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundInit.CLOSE_SHADOW_CHEST_EVENT, SoundSource.BLOCKS,
                        0.5F, world.getRandom().nextFloat() * 0.1F + 0.9F);
            }

            @Override
            protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                world.blockEvent(ShadowChestEntity.this.worldPosition, BlockInit.SHADOW_CHEST, 1, newViewerCount);
            }

            @Override
            public boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container inventory = ((ChestMenu) player.containerMenu).getContainer();
                    return inventory == ShadowChestEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, ShadowChestEntity blockEntity) {
        blockEntity.lidAnimator.tickLid();
        if (((ChestLidAnimatorAccessor) blockEntity.lidAnimator).getOpen()) {
            for (int i = 0; i < 3; ++i) {
                int j = world.getRandom().nextInt(2) * 2 - 1;
                int k = world.getRandom().nextInt(2) * 2 - 1;
                double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
                double e = (double) ((float) pos.getY() + world.getRandom().nextFloat());
                double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
                double g = (double) world.getRandom().nextFloat() * (float) j * 0.1D;
                double h = (double) world.getRandom().nextFloat() * 0.4D;
                double l = (double) world.getRandom().nextFloat() * (float) k * 0.1D;
                world.addParticle(ParticleTypes.END_ROD, d, e, f, g, h, l);
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory);
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
    }

    @Override
    public boolean triggerEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.shouldBeOpen(data > 0);
            return true;
        } else {
            return super.triggerEvent(type, data);
        }
    }

    @Override
    public void startOpen(ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.stateManager.incrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), containerUser.getContainerInteractionRange());
        }

    }

    @Override
    public void stopOpen(ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.stateManager.decrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
        if (this.isEmpty()) {
            this.getLevel().destroyBlock(worldPosition, true);
        }

    }

    @Override
    public boolean stillValid(Player player) {
        if (this.getLevel().getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    public void onScheduledTick() {
        if (!this.remove) {
            this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    @Override
    public float getOpenNess(float tickDelta) {
        return this.lidAnimator.getOpenness(tickDelta);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("adventurez.container.shadowchest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return ChestMenu.threeRows(syncId, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public void setRandomLoot() {
        if (!this.getLevel().isClientSide()) {
            List<ItemStack> tableList = this.getLevel().getServer().reloadableRegistries().getLootTable(BuiltInLootTables.END_CITY_TREASURE)
                    .getRandomItems(new LootParams.Builder((ServerLevel) this.getLevel()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).create(LootContextParamSets.CHEST));

            for (int i = 0; i < 27; i++) {
                if (i == 20 && this.getLevel().getRandom().nextFloat() < 0.2F && FabricLoader.getInstance().isModLoaded("medievalweapons")) {
                    this.inventory.set(i, new ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("medievalweapons", "thalleous_sword"))));
                    continue;
                }
                if (i == 13)
                    this.inventory.set(i, new ItemStack(ItemInit.SOURCE_STONE));
                else if (i == 1)
                    this.inventory.set(i, new ItemStack(Items.DRAGON_EGG));
                else {
                    switch (this.getLevel().getRandom().nextInt(10)) {
                        case 0:
                            this.inventory.set(i, new ItemStack(Items.ENDER_PEARL, this.getLevel().getRandom().nextInt(5) + 1));
                            break;
                        case 4:
                            this.inventory.set(i, new ItemStack(Items.GOLDEN_APPLE, 1));
                            break;
                        case 5:
                            this.inventory.set(i, new ItemStack(Items.EXPERIENCE_BOTTLE, this.getLevel().getRandom().nextInt(12) + 1));
                            break;
                        case 6:
                            ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
                            RegistryAccess dynamicRegistryManager = this.getLevel().registryAccess();
                            Optional<Holder.Reference<Enchantment>> optional = dynamicRegistryManager.lookupOrThrow(Registries.ENCHANTMENT).getRandom(this.getLevel().getRandom());
                            if (optional.isPresent()) {
                                stack.enchant(optional.get(), optional.get().value().getMaxLevel() + (ConfigInit.CONFIG.allow_special_enchant_loot ? 1 : 0));
                            }
                            stack.set(ItemInit.VOID_DROP, true);

                            this.inventory.set(i, stack);
                            break;
                        case 7:
                            this.inventory.set(i, tableList.get(this.getLevel().getRandom().nextInt(tableList.size())));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

}
