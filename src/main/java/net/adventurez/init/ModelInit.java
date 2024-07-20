package net.adventurez.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

@Environment(EnvType.CLIENT)
public class ModelInit {

    public static void init() {
        ModelPredicateProviderRegistry.register(ItemInit.BLACKSTONE_GOLEM_ARM, Identifier.of("lavalight"), (stack, world, entity, seed) -> {
            if (stack.get(ItemInit.LAVA_LIGHT) != null && stack.get(ItemInit.LAVA_LIGHT)) {
                return 1F;
            }
            return 0F;
        });

    }

}