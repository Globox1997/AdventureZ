package net.adventurez.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.adventurez.init.EffectInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "prepareOffersFor", at = @At(value = "TAIL"))
    private void prepareOffersForMixin(PlayerEntity player, CallbackInfo info) {
        if (player.hasStatusEffect(EffectInit.FAME) && !player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
            Iterator<TradeOffer> var5 = this.getOffers().iterator();

            while (var5.hasNext()) {
                TradeOffer tradeOffer2 = (TradeOffer) var5.next();
                int k = (int) Math.floor(0.5D * (double) tradeOffer2.getOriginalFirstBuyItem().getCount());
                tradeOffer2.increaseSpecialPrice(-Math.max(k, 1));
            }
        }
    }
}
