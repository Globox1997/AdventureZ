package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.VoidFragmentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VoidFragmentModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart small;
    private final ModelPart small1;
    private final ModelPart small2;
    private final ModelPart small3;
    private final ModelPart small4;
    private final ModelPart small5;
    private final ModelPart small6;
    private final ModelPart big;
    private final ModelPart big1;
    private final ModelPart big2;
    private final ModelPart big3;
    private final ModelPart big4;
    private final ModelPart big5;

    public VoidFragmentModel(ModelPart root) {
        this.small = root.getChild("small");
        this.small6 = this.small.getChild("small6");
        this.small5 = this.small.getChild("small5");
        this.small4 = this.small.getChild("small4");
        this.small3 = this.small.getChild("small3");
        this.small2 = this.small.getChild("small2");
        this.small1 = this.small.getChild("small1");
        this.big = root.getChild("big");
        this.big5 = this.big.getChild("big5");
        this.big4 = this.big.getChild("big4");
        this.big3 = this.big.getChild("big3");
        this.big2 = this.big.getChild("big2");
        this.big1 = this.big.getChild("big1");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("small", ModelPartBuilder.create().uv(16, 0).cuboid(-1.5F, -7.0F, -1.5F, 3.0F, 13.0F, 3.0F), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        modelPartData1.addChild("small1", ModelPartBuilder.create().uv(16, 32).cuboid(3.5F, -16.0F, 1.5F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData1.addChild("small2", ModelPartBuilder.create().uv(0, 0).cuboid(1.5F, -11.0F, -2.5F, 1.0F, 3.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData1.addChild("small3", ModelPartBuilder.create().uv(30, 30).cuboid(-4.5F, -11.0F, 0.5F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData1.addChild("small4", ModelPartBuilder.create().uv(32, 15).cuboid(-2.5F, -5.0F, -4.5F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData1.addChild("small5", ModelPartBuilder.create().uv(12, 0).cuboid(-1.5F, -7.0F, 2.5F, 1.0F, 2.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        modelPartData1.addChild("small6", ModelPartBuilder.create().uv(28, 11).cuboid(3.5F, -6.0F, 0.5F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("big", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -11.0F, -2.0F, 4.0F, 23.0F, 4.0F), ModelTransform.pivot(0.0F, 6.0F, 0.0F));
        modelPartData2.addChild("big1", ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, -21.0F, 4.0F, 2.0F, 14.0F, 2.0F), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        modelPartData2.addChild("big2", ModelPartBuilder.create().uv(16, 16).cuboid(-6.0F, -25.0F, -3.0F, 2.0F, 14.0F, 2.0F), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        modelPartData2.addChild("big3", ModelPartBuilder.create().uv(28, 0).cuboid(5.0F, -11.0F, -6.0F, 2.0F, 9.0F, 2.0F), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        modelPartData2.addChild("big4", ModelPartBuilder.create().uv(8, 27).cuboid(-1.0F, -32.0F, -7.0F, 2.0F, 9.0F, 2.0F), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        modelPartData2.addChild("big5", ModelPartBuilder.create().uv(0, 27).cuboid(3.0F, -17.0F, 2.0F, 2.0F, 11.0F, 2.0F), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.small, this.big);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.getDataTracker().get(VoidFragmentEntity.IS_VOID_ORB)) {
            big.visible = true;
            small.visible = false;
            this.big.pivotY = 6.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
            this.big1.pivotY = 16.0F + MathHelper.cos(animationProgress * 0.2662F + MathHelper.PI * 0.3F) * 0.9F;
            this.big2.pivotY = 16.0F + MathHelper.cos(animationProgress * 0.2662F + MathHelper.PI * 0.6F) * 1.2F;
            this.big3.pivotY = 16.0F + MathHelper.cos(animationProgress * 0.2662F + MathHelper.PI * 1.3F) * 0.7F;
            this.big4.pivotY = 16.0F + MathHelper.cos(animationProgress * 0.2662F + MathHelper.PI * 1.6F) * 0.8F;
            this.big5.pivotY = 16.0F + MathHelper.cos(animationProgress * 0.2662F + MathHelper.PI) * 0.6F;
        } else {
            big.visible = false;
            small.visible = true;
            this.small.pivotY = 12.0F + MathHelper.cos(animationProgress * 0.1662F) * 0.6F;
            this.small1.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI * 0.3F) * 0.9F;
            this.small2.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI * 0.6F) * 1.2F;
            this.small3.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI * 1.3F) * 0.7F;
            this.small4.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI * 1.6F) * 0.8F;
            this.small5.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI) * 0.6F;
            this.small6.pivotY = 8.0F + MathHelper.cos(animationProgress * 0.1662F + MathHelper.PI) * 0.4F;
        }
    }
}