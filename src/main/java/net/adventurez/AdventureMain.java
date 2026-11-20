package net.adventurez;

import net.adventurez.init.*;
import net.adventurez.network.AdventureServerPacket;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class AdventureMain implements ModInitializer {

    @Override
    public void onInitialize() {
        BlockInit.init();
        ConfigInit.init();
        EffectInit.init();
        EntityInit.init();
        ItemInit.init();
        AdventureServerPacket.init();
        LootInit.init();
        ParticleInit.init();
        SoundInit.init();
        SpawnInit.init();
        TagInit.init();
        EventInit.init();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.fromNamespaceAndPath("adventurez", name);
    }
}

// You are LOVED!!!
// Jesus loves you unconditional!