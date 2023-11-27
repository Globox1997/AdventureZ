package net.adventurez.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.nbt.NbtCompound;

@Environment(EnvType.CLIENT)
public class ModelInit {

    public static void init() {
        ModelPredicateProviderRegistry.register(ItemInit.BLACKSTONE_GOLEM_ARM, new Identifier("lavalight"), (stack, world, entity, seed) -> {
            NbtCompound tags = stack.getNbt();
            if (stack.hasNbt() && tags.getBoolean("lavalight")) {
                return 1F;
            }
            return 0F;
        });

    }

}