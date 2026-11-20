package net.adventurez.neoforge.platform;

import java.util.List;
import java.util.function.Supplier;

import net.adventurez.platform.services.IPlatformHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        ModList modList = ModList.get();
        if (modList != null) {
            return modList.isLoaded(modId);
        }
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(modId) != null;
    }

    @Override
    public CreativeModeTab createCreativeModeTab(Component title, Supplier<ItemStack> icon, Supplier<List<Item>> displayItems) {
        return CreativeModeTab.builder().title(title).icon(icon).displayItems((parameters, output) -> displayItems.get().forEach(output::accept)).build();
    }

}
