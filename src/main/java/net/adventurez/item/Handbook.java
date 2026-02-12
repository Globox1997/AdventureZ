package net.adventurez.item;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public class Handbook extends Item {

    private final boolean isPatchouliLoaded = FabricLoader.getInstance().isModLoaded("patchouli");

    private static final List<RawFilteredPair<Text>> pages =
            List.of(
                    RawFilteredPair.of(Text.literal("You may ask yourself, what does NRTTM mean... It means: Not related to the mod :D This are just some sentences I put together because I like to do it^^ First of all, thank you for playing the AdventureZ mod, I hope it will give your world a little bit more of a challenge and you like it :) But what I really want to tell you is: You are appreciated and loved as the person you are!")),
                    RawFilteredPair.of(Text.literal("I know this sounds a little cheesy but thats how it is: You are loved! My name is Micha, in some games I took the name Globox (because I like the character of Rayman ;)), I'm a normal person just like you (I know everybody is special of course but you get the point) and I hope you believe deep inside yourself that you are a loved person. Hopefully by people you know closely, if not, there will be people who will love you just as you are. Why am I writing this? Good question :D")),
                    RawFilteredPair.of(Text.literal("Because from time to time I feel like people believe in this specific aspect a lie, that they are not loved or something even worse. Also I think, except of human needs, love is the most important thing in life you need. It is just on my heart to say to you: You are loved! I mean, I do not know you and we might be not on a same wavelength in our personality and we would perhaps even not hang out as friends if we know each other but that doesn't change the truth about you")),
                    RawFilteredPair.of(Text.literal("(which I believe and hopefully you too). You might be young or maybe a little older, at one point in your life you should figure out why and for what reason you live here in this world (basically the meaning of your life), hopefully you already found it but let me tell you something: It is a great process everybody has to go through :D (I don't know how I got to the meaning of life right now... mh...). However I might edit this text a few times^^.")),
                    RawFilteredPair.of(Text.literal("I wish you the best, if you got questions about the mods or anything else, hit me up on Discord (Globox_Z #1024) or anywhere else"))
            );


    private static final WrittenBookContentComponent writtenBookContentComponent = new WrittenBookContentComponent(RawFilteredPair.of("NRTTM"), "Globox_Z", 1, pages, true);

    public Handbook(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (isPatchouliLoaded) {
            if (!world.isClient()) {
                PatchouliAPI.get().openBookGUI((ServerPlayerEntity) user, Identifier.of("adventurez", "adventurez"));
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        } else {
            if (!user.getStackInHand(hand).contains(DataComponentTypes.WRITTEN_BOOK_CONTENT)) {
                user.getStackInHand(hand).set(DataComponentTypes.WRITTEN_BOOK_CONTENT, writtenBookContentComponent);
            }
            if (!world.isClient()) {
                ((ServerPlayerEntity) user).networkHandler.sendPacket(new OpenWrittenBookS2CPacket(hand));
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (!isPatchouliLoaded) {
            tooltip.add(Text.translatable("item.adventurez.patchouli_book.tooltip.1"));
            tooltip.add(Text.translatable("item.adventurez.patchouli_book.tooltip.2"));
        }
        tooltip.add(Text.translatable("book.byAuthor", "Globox_Z").formatted(Formatting.GRAY));
    }

}
