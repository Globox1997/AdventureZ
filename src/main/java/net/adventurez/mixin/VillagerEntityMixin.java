package net.adventurez.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.EffectInit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager {
    public VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "updateSpecialPrices", at = @At(value = "TAIL"))
    private void prepareOffersForMixin(Player player, CallbackInfo info) {
        if (player.hasEffect(EffectInit.FAME) && !player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
            Iterator<MerchantOffer> var5 = this.getOffers().iterator();

            while (var5.hasNext()) {
                MerchantOffer tradeOffer2 = (MerchantOffer) var5.next();
                int k = (int) Math.floor(0.5D * (double) tradeOffer2.getBaseCostA().getCount());
                tradeOffer2.addToSpecialPriceDiff(-Math.max(k, 1));
            }
        }
    }
}
