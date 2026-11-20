package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.adventurez.AdventureMain;
import net.adventurez.entity.SoulReaperEntity;
import net.adventurez.entity.model.SoulReaperModel;
import net.adventurez.entity.render.feature.SoulReaperEyesFeatureRenderer;
import net.adventurez.entity.render.state.SoulReaperRenderState;
import net.adventurez.init.RenderInit;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Items;

public class SoulReaperRenderer extends HumanoidMobRenderer<SoulReaperEntity, SoulReaperRenderState, SoulReaperModel> {
    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/entity/soul_reaper.png");

    public SoulReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulReaperModel(context.bakeLayer(RenderInit.SOUL_REAPER_LAYER)), 0.5F);
        this.addLayer(new SoulReaperEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(SoulReaperRenderState soulReaperEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(SoulReaperRenderState soulReaperEntity, PoseStack matrixStack) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    protected HumanoidModel.ArmPose getArmPose(SoulReaperEntity mob, HumanoidArm arm) {
        if (arm == HumanoidArm.RIGHT && mob.getMainHandItem().is(Items.BOW) && mob.isAggressive() && !mob.isPassenger()) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
        return HumanoidModel.ArmPose.EMPTY;
    }

    @Override
    public SoulReaperRenderState createRenderState() {
        return new SoulReaperRenderState();
    }

    @Override
    public void extractRenderState(SoulReaperEntity entity, SoulReaperRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.aggressive = entity.isAggressive();
        state.holdingBow = entity.getMainHandItem().is(Items.BOW);
        state.mainHandEmpty = entity.getMainHandItem().isEmpty();
    }
}
