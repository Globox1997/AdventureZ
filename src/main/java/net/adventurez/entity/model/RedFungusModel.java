package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.RedFungusEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class RedFungusModel<T extends RedFungusEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public RedFungusModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFoot = root.getChild("leftFoot");
        this.rightFoot = root.getChild("rightFoot");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -5.0304F, -7.6528F, 16.0F, 7.0F, 15.0F).uv(0, 22).cuboid(-6.0F, -7.0304F, -5.6528F, 12.0F, 2.0F, 11.0F),
                ModelTransform.pivot(0.0F, 10.0F, 0.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 35).cuboid(-4.0F, -4.0F, -3.0F, 8.0F, 7.0F, 5.0F), ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        modelPartData.addChild("leftFoot", ModelPartBuilder.create().uv(26, 35).cuboid(-1.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-2.0F, 18.0F, -0.5F));
        modelPartData.addChild("rightFoot", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(2.0F, 18.0F, -0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.leftFoot, this.rightFoot);
    }

    @Override
    public void setAngles(T fungus, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = -0.1745F;
        this.rightFoot.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftFoot.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        if (k > 0) {
            this.head.pitch = -k * 1.2F;
        }

    }

}
