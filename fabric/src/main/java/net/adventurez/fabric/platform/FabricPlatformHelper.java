package net.adventurez.fabric.platform;

import java.util.List;
import java.util.function.Supplier;

import net.adventurez.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public CreativeModeTab createCreativeModeTab(Component title, Supplier<ItemStack> icon, Supplier<List<Item>> displayItems) {
        return FabricCreativeModeTab.builder().title(title).icon(icon).displayItems((parameters, output) -> displayItems.get().forEach(output::accept)).build();
    }

}
