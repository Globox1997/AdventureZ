package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.EnderwarthogEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EnderwarthogModel<T extends EnderwarthogEntity> extends CompositeEntityModel<T> {
    private final ModelPart mainbody;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart ribbon1L;
    private final ModelPart ribbon2L;
    private final ModelPart ribbon1R;
    private final ModelPart ribbon2R;
    private final ModelPart horn1L;
    private final ModelPart horn2L;
    private final ModelPart horn3L;
    private final ModelPart horn1R;
    private final ModelPart horn2R;
    private final ModelPart horn3R;
    private final ModelPart legfL;
    private final ModelPart legbL;
    private final ModelPart legfR;
    private final ModelPart legbR;

    public EnderwarthogModel(ModelPart root) {
        this.mainbody = root.getChild("mainbody");
        this.legbR = this.mainbody.getChild("legbR");
        this.legfR = this.mainbody.getChild("legfR");
        this.legbL = this.mainbody.getChild("legbL");
        this.legfL = this.mainbody.getChild("legfL");
        this.neck = this.mainbody.getChild("neck");
        this.horn1R = this.neck.getChild("horn1R");
        this.horn2R = this.horn1R.getChild("horn2R");
        this.horn3R = this.horn2R.getChild("horn3R");
        this.horn1L = this.neck.getChild("horn1L");
        this.horn2L = this.horn1L.getChild("horn2L");
        this.horn3L = this.horn2L.getChild("horn3L");
        this.head = this.neck.getChild("head");
        this.ribbon1R = this.head.getChild("ribbon1R");
        this.ribbon2R = this.ribbon1R.getChild("ribbon2R");
        this.ribbon1L = this.head.getChild("ribbon1L");
        this.ribbon2L = this.ribbon1L.getChild("ribbon2L");
        this.jaw = this.head.getChild("jaw");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("mainbody", ModelPartBuilder.create().uv(0, 190).cuboid(-10.5F, -8.5F, -16.5F, 21.0F, 19.0F, 36.0F),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData1.addChild("neck", ModelPartBuilder.create().uv(0, 10).cuboid(-8.5F, -7.5F, -15.0F, 17.0F, 18.0F, 15.0F),
                ModelTransform.pivot(0.0F, -3.5F, -10.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild("head", ModelPartBuilder.create().uv(180, 20).cuboid(-6.5F, -3.6F, -13.0F, 13.0F, 7.0F, 17.0F),
                ModelTransform.pivot(0.0F, -2.0F, -17.0F));
        ModelPartData modelPartData4 = modelPartData3.addChild("jaw", ModelPartBuilder.create().uv(180, 60).cuboid(-7.0F, -2.5F, -16.0F, 14.0F, 5.0F, 18.0F), ModelTransform.pivot(0.0F, 6.0F, 2.0F));
        modelPartData4.addChild("jawguard", ModelPartBuilder.create().uv(180, 100).cuboid(-7.0F, -3.0F, -8.0F, 14.0F, 3.0F, 18.0F), ModelTransform.pivot(0.0F, -2.0F, -8.0F));
        ModelPartData modelPartData5 = modelPartData3.addChild("ribbon1L", ModelPartBuilder.create().uv(140, 0).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F), ModelTransform.pivot(-4.0F, -3.5F, 0.0F));
        modelPartData5.addChild("ribbon2L", ModelPartBuilder.create().uv(140, 20).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F), ModelTransform.pivot(0.0F, 0.0F, 9.0F));
        ModelPartData modelPartData6 = modelPartData3.addChild("ribbon1R", ModelPartBuilder.create().uv(140, 0).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, true),
                ModelTransform.pivot(4.0F, -3.5F, 0.0F));
        modelPartData6.addChild("ribbon2R", ModelPartBuilder.create().uv(140, 20).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, true), ModelTransform.pivot(0.0F, 0.0F, 9.0F));
        ModelPartData modelPartData7 = modelPartData2.addChild("horn1L", ModelPartBuilder.create().uv(80, 0).cuboid(-10.0F, -3.5F, -5.0F, 10.0F, 7.0F, 7.0F, true),
                ModelTransform.pivot(-6.0F, -2.0F, -9.1F));
        ModelPartData modelPartData8 = modelPartData7.addChild("horn2L", ModelPartBuilder.create().uv(80, 20).cuboid(-10.0F, -2.5F, -2.5F, 10.0F, 5.0F, 5.0F, true),
                ModelTransform.pivot(-8.0F, -1.0F, -1.0F));
        modelPartData8.addChild("horn3L", ModelPartBuilder.create().uv(80, 40).cuboid(-8.0F, -2.5F, -2.5F, 9.0F, 4.0F, 4.0F, true), ModelTransform.pivot(-9.0F, 0.4F, 0.0F));
        ModelPartData modelPartData9 = modelPartData2.addChild("horn1R", ModelPartBuilder.create().uv(80, 0).cuboid(0.0F, -3.5F, -5.0F, 10.0F, 7.0F, 7.0F), ModelTransform.pivot(6.0F, 0.0F, -8.9F));
        ModelPartData modelPartData10 = modelPartData9.addChild("horn2R", ModelPartBuilder.create().uv(80, 20).cuboid(0.0F, -2.5F, -2.5F, 10.0F, 5.0F, 5.0F),
                ModelTransform.pivot(8.0F, -1.0F, -1.0F));
        modelPartData10.addChild("horn3R", ModelPartBuilder.create().uv(80, 40).cuboid(-1.0F, -2.5F, -2.5F, 9.0F, 4.0F, 4.0F), ModelTransform.pivot(9.0F, 0.4F, 0.0F));
        ModelPartData modelPartData11 = modelPartData1.addChild("legfL", ModelPartBuilder.create().uv(0, 120).cuboid(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                ModelTransform.pivot(-10.0F, -3.0F, -11.0F));
        modelPartData11.addChild("thighfLL", ModelPartBuilder.create().uv(40, 130).cuboid(-7.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new Dilation(0.3F)), ModelTransform.pivot(3.0F, 0.0F, 0.0F));
        ModelPartData modelPartData12 = modelPartData1.addChild("legbL", ModelPartBuilder.create().uv(0, 70).cuboid(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                ModelTransform.pivot(-10.0F, -3.0F, 13.0F));
        modelPartData12.addChild("thighbL", ModelPartBuilder.create().uv(40, 90).cuboid(-8.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new Dilation(0.3F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));
        ModelPartData modelPartData13 = modelPartData1.addChild("legfR", ModelPartBuilder.create().uv(0, 120).cuboid(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                ModelTransform.pivot(10.0F, -3.0F, -11.0F));
        modelPartData13.addChild("thighfLR", ModelPartBuilder.create().uv(40, 130).cuboid(0.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new Dilation(0.3F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));
        ModelPartData modelPartData14 = modelPartData1.addChild("legbR", ModelPartBuilder.create().uv(0, 70).cuboid(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F), ModelTransform.pivot(10.0F, -3.0F, 13.0F));
        modelPartData14.addChild("thighbR", ModelPartBuilder.create().uv(40, 90).cuboid(0.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new Dilation(0.3F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.mainbody);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        this.head.pitch = 0.2602F + headPitch * 0.010453292F;
        this.neck.yaw = headYaw * 0.004453292F;
        this.head.yaw = headYaw * 0.005453292F;
        this.jaw.pitch = 0.0524F;
        this.ribbon1L.pitch = 0.7854F;
        this.ribbon1L.yaw = -0.4712F;
        this.ribbon2L.pitch = -0.3491F;
        this.ribbon2L.yaw = 0.2443F;
        this.ribbon1R.pitch = 0.7854F;
        this.ribbon1R.yaw = 0.4712F;
        this.ribbon2R.pitch = -0.3491F;
        this.ribbon2R.yaw = -0.2443F;
        this.horn1L.pitch = 0.1022F;
        this.horn1L.yaw = -0.2274F;
        this.horn1L.roll = -0.8633F;
        this.horn2L.pitch = -0.0524F;
        this.horn2L.yaw = -0.5411F;
        this.horn2L.roll = -0.3142F;
        this.horn3L.pitch = 0.2269F;
        this.horn3L.yaw = -0.576F;
        this.horn3L.roll = -0.2793F;
        this.horn1R.pitch = -0.1047F;
        this.horn1R.yaw = 0.3142F;
        this.horn1R.roll = 0.8727F;
        this.horn2R.pitch = 0.0524F;
        this.horn2R.yaw = 0.5411F;
        this.horn2R.roll = 0.3142F;
        this.horn3R.pitch = 0.2269F;
        this.horn3R.yaw = 0.576F;
        this.horn3R.roll = 0.2793F;

        this.legbR.pitch = MathHelper.cos(limbAngle * 0.6662F) * 0.8F * limbDistance;
        this.legbL.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 0.8F * limbDistance;
        this.legfR.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 0.8F * limbDistance;
        this.legfL.pitch = MathHelper.cos(limbAngle * 0.6662F) * 0.8F * limbDistance;

        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);

        if (k > 0.0F) {
            if (entity.getDataTracker().get(EnderwarthogEntity.BITE_ATTACK)) {
                this.neck.pitch = -k * 0.05F;
                this.head.pitch = -k * 0.01F;
                this.jaw.pitch = 0.0524F + k * 0.45F;
            } else {
                this.neck.pitch = -k * 0.4F;
                this.head.pitch = -k * 0.2F;
            }
        } else {
            this.neck.pitch = 0.0F;
        }
    }

}
