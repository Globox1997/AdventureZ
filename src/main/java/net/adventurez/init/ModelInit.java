package net.adventurez.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ModelInit {

    public static void init() {
        ItemProperties.register(ItemInit.BLACKSTONE_GOLEM_ARM, ResourceLocation.parse("lavalight"), (stack, world, entity, seed) -> {
            if (stack.get(ItemInit.LAVA_LIGHT) != null && stack.get(ItemInit.LAVA_LIGHT)) {
                return 1F;
            }
            return 0F;
        });

    }

}