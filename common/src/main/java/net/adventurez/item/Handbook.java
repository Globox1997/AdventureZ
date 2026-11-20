package net.adventurez.item;

import net.adventurez.AdventureMain;
import net.adventurez.init.ConfigInit;
import net.adventurez.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

public class Handbook extends Item {

    private final boolean isPatchouliLoaded = Services.PLATFORM.isModLoaded("patchouli");

    private static final List<Filterable<Component>> PAGES = List.of(
            Filterable.passThrough(Component.literal("You may ask yourself, what does NRTTM mean... It does mean: Not related to the mod :D This are just some sentences I put together because I like to do it^^ First of all, thank you for playing the AdventureZ mod, I hope it will give your world a little bit more of a challenge and")),
            Filterable.passThrough(Component.literal("you like it :) But what I really want to tell you is: You are appreciated and loved as the person you are! I know this sounds a little cheesy but thats how it is: You are loved! My name is Micha, in some games I took the name Globox (because I like the character of Rayman ;)),")),
            Filterable.passThrough(Component.literal("I'm a normal person just like you (I know everybody is special of course but you get the point) and I hope you believe deep inside yourself that you are a loved person. Hopefully by people you know closely, if not, there will be people who will love you just as you are.")),
            Filterable.passThrough(Component.literal("Why am I writing this? Good question :D Because from time to time I feel like people believe in this specific aspect a lie, that they are not loved or something even worse. Also I think, except of human needs, love is the most important thing in life you need.")),
            Filterable.passThrough(Component.literal("It is just on my heart to say to you: You are loved! I mean, I do not know you and we might be not on a same wavelength in our personality and we would perhaps even not hang out as friends if we know each other but that doesn't change the truth about you")),
            Filterable.passThrough(Component.literal("(which I believe and hopefully you too). You might be young or maybe a little older, at one point in your life you should figure out why and for what reason you live here in this world (basically the meaning of your life),")),
            Filterable.passThrough(Component.literal("hopefully you already found it but let me tell you something: It is a great process everybody has to go through :D (I don't know how I got to the meaning of life right now... mh...). However I might edit this text a few times^^.")),
            Filterable.passThrough(Component.literal("I wish you the best, if you got questions about the mods or anything else, hit me up on Discord (Globox_Z #1024) or anywhere else"))
    );

    private static final WrittenBookContent writtenBookContentComponent = new WrittenBookContent(Filterable.passThrough("NRTTM"), "Globox_Z", 1, PAGES, true);

    public Handbook(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (isPatchouliLoaded) {
            if (!world.isClientSide()) {
                PatchouliAPI.get().openBookGUI((ServerPlayer) user, AdventureMain.identifierOf("adventurez"));
                return InteractionResult.SUCCESS;
            }
        } else {
            if (!user.getItemInHand(hand).has(DataComponents.WRITTEN_BOOK_CONTENT)) {
                user.getItemInHand(hand).set(DataComponents.WRITTEN_BOOK_CONTENT, writtenBookContentComponent);
            }
            if (!world.isClientSide()) {
                ((ServerPlayer) user).connection.send(new ClientboundOpenBookPacket(hand));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (ConfigInit.CONFIG.allow_extra_tooltips) {
            if (!isPatchouliLoaded) {
                tooltip.accept(Component.translatable("item.adventurez.patchouli_book.tooltip.1"));
                tooltip.accept(Component.translatable("item.adventurez.patchouli_book.tooltip.2"));
            }
        }
        tooltip.accept(Component.translatable("book.byAuthor", "Globox_Z").withStyle(ChatFormatting.GRAY));
    }

}
