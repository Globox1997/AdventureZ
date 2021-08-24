package net.adventurez.init;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
//import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class ModelProviderInit {

    public static void init() {
        FabricModelPredicateProviderRegistry.register(ItemInit.STONE_GOLEM_ARM, new Identifier("lavalight"), (stack, world, entity, seed) -> {
            NbtCompound tags = stack.getNbt();
            if (stack.hasNbt() && tags.getBoolean("lavalight")) {
                return 1F;
            }
            return 0F;
        });

    }

}