package net.adventurez.block.entity;

import java.util.List;
import java.util.stream.Collectors;

import net.adventurez.init.BlockInit;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.ItemInit;
import net.adventurez.init.SoundInit;
import net.adventurez.mixin.accessor.ChestLidAnimatorAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ShadowChestEntity extends LootableContainerBlockEntity implements ChestAnimationProgress {

    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();
    private DefaultedList<ItemStack> inventory;
    private ViewerCountManager stateManager;

    public ShadowChestEntity(BlockPos pos, BlockState state) {
        super(BlockInit.SHADOW_CHEST_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        this.stateManager = new ViewerCountManager() {
            @Override
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundInit.OPEN_SHADOW_CHEST_EVENT, SoundCategory.BLOCKS, 0.5F,
                        world.random.nextFloat() * 0.1F + 0.9F);
            }

            @Override
            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundInit.CLOSE_SHADOW_CHEST_EVENT, SoundCategory.BLOCKS,
                        0.5F, world.random.nextFloat() * 0.1F + 0.9F);
            }

            @Override
            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                world.addSyncedBlockEvent(ShadowChestEntity.this.pos, BlockInit.SHADOW_CHEST_BLOCK, 1, newViewerCount);
            }

            @Override
            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                    return inventory == ShadowChestEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, ShadowChestEntity blockEntity) {
        blockEntity.lidAnimator.step();
        if (((ChestLidAnimatorAccessor) blockEntity.lidAnimator).getOpen()) {
            for (int i = 0; i < 3; ++i) {
                int j = world.random.nextInt(2) * 2 - 1;
                int k = world.random.nextInt(2) * 2 - 1;
                double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
                double e = (double) ((float) pos.getY() + world.random.nextFloat());
                double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
                double g = (double) (world.random.nextFloat() * (float) j * 0.1D);
                double h = (double) world.random.nextFloat() * 0.4D;
                double l = (double) (world.random.nextFloat() * (float) k * 0.1D);
                world.addParticle(ParticleTypes.END_ROD, d, e, f, g, h, l);
            }
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }

        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }

    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.setOpen(data > 0);
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
        if (this.isEmpty()) {
            this.world.breakBlock(pos, true);
        }

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return this.lidAnimator.getProgress(tickDelta);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("adventurez.container.shadowchest");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    public void setRandomLoot() {
        for (int i = 0; i < 27; i++) {
            if (i == 20 && this.world.random.nextFloat() < 0.2F && FabricLoader.getInstance().isModLoaded("medievalweapons")) {
                this.inventory.set(i, new ItemStack(net.medievalweapons.init.ItemInit.THALLEOUS_SWORD));
                continue;
            }
            if (i == 13) {
                this.inventory.set(i, new ItemStack(ItemInit.SOURCE_STONE));
            } else {
                switch (this.world.random.nextInt(10)) {
                    case 0:
                        this.inventory.set(i, new ItemStack(Items.ENDER_PEARL, this.world.random.nextInt(5) + 1));
                        break;
                    case 4:
                        this.inventory.set(i, new ItemStack(Items.GOLDEN_APPLE, 1));
                        break;
                    case 5:
                        this.inventory.set(i, new ItemStack(Items.EXPERIENCE_BOTTLE, this.world.random.nextInt(12) + 1));
                        break;
                    case 6:
                        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
                        List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::isAvailableForRandomSelection).collect(Collectors.toList());
                        Enchantment enchantment = list.get(world.random.nextInt(list.size()));
                        EnchantedBookItem.addEnchantment(stack,
                                new EnchantmentLevelEntry(enchantment, ConfigInit.CONFIG.allow_special_enchant_loot ? enchantment.getMaxLevel() + 1 : enchantment.getMaxLevel()));
                        this.inventory.set(i, stack);
                        break;
                    case 7:
                        List<ItemStack> tableList = this.world.getServer().getLootManager().getTable(LootTables.END_CITY_TREASURE_CHEST)
                                .generateLoot((new LootContext.Builder((ServerWorld) this.world)).parameter(LootContextParameters.ORIGIN, new Vec3d(0.0D, 0.0D, 0.0D)).random(this.world.random)
                                        .build(LootContextTypes.CHEST));
                        this.inventory.set(i, tableList.get(this.world.random.nextInt(tableList.size())));
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
