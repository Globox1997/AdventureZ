package net.adventurez;

import net.adventurez.init.ConfigInit;
import net.minecraft.resources.Identifier;

public class AdventureMain {

    public static final String MOD_ID = "adventurez";

    public static void init() {
        ConfigInit.init();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}

// You are LOVED!!!
// Jesus loves you unconditional!