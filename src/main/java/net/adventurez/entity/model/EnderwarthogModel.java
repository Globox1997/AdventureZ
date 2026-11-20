package net.adventurez.entity.model;


import net.adventurez.entity.render.state.EnderwarthogRenderState;
import net.minecraft.client.model.EntityModel;
import net.adventurez.entity.EnderwarthogEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class EnderwarthogModel extends EntityModel<EnderwarthogRenderState> {
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
        super(root);
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

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("mainbody", CubeListBuilder.create().texOffs(0, 190).addBox(-10.5F, -8.5F, -16.5F, 21.0F, 19.0F, 36.0F),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 10).addBox(-8.5F, -7.5F, -15.0F, 17.0F, 18.0F, 15.0F),
                PartPose.offset(0.0F, -3.5F, -10.0F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(180, 20).addBox(-6.5F, -3.6F, -13.0F, 13.0F, 7.0F, 17.0F),
                PartPose.offset(0.0F, -2.0F, -17.0F));
        PartDefinition modelPartData4 = modelPartData3.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(180, 60).addBox(-7.0F, -2.5F, -16.0F, 14.0F, 5.0F, 18.0F), PartPose.offset(0.0F, 6.0F, 2.0F));
        modelPartData4.addOrReplaceChild("jawguard", CubeListBuilder.create().texOffs(180, 100).addBox(-7.0F, -3.0F, -8.0F, 14.0F, 3.0F, 18.0F), PartPose.offset(0.0F, -2.0F, -8.0F));
        PartDefinition modelPartData5 = modelPartData3.addOrReplaceChild("ribbon1L", CubeListBuilder.create().texOffs(140, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F), PartPose.offset(-4.0F, -3.5F, 0.0F));
        modelPartData5.addOrReplaceChild("ribbon2L", CubeListBuilder.create().texOffs(140, 20).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F), PartPose.offset(0.0F, 0.0F, 9.0F));
        PartDefinition modelPartData6 = modelPartData3.addOrReplaceChild("ribbon1R", CubeListBuilder.create().texOffs(140, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, true),
                PartPose.offset(4.0F, -3.5F, 0.0F));
        modelPartData6.addOrReplaceChild("ribbon2R", CubeListBuilder.create().texOffs(140, 20).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, true), PartPose.offset(0.0F, 0.0F, 9.0F));
        PartDefinition modelPartData7 = modelPartData2.addOrReplaceChild("horn1L", CubeListBuilder.create().texOffs(80, 0).addBox(-10.0F, -3.5F, -5.0F, 10.0F, 7.0F, 7.0F, true),
                PartPose.offset(-6.0F, -2.0F, -9.1F));
        PartDefinition modelPartData8 = modelPartData7.addOrReplaceChild("horn2L", CubeListBuilder.create().texOffs(80, 20).addBox(-10.0F, -2.5F, -2.5F, 10.0F, 5.0F, 5.0F, true),
                PartPose.offset(-8.0F, -1.0F, -1.0F));
        modelPartData8.addOrReplaceChild("horn3L", CubeListBuilder.create().texOffs(80, 40).addBox(-8.0F, -2.5F, -2.5F, 9.0F, 4.0F, 4.0F, true), PartPose.offset(-9.0F, 0.4F, 0.0F));
        PartDefinition modelPartData9 = modelPartData2.addOrReplaceChild("horn1R", CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, -3.5F, -5.0F, 10.0F, 7.0F, 7.0F), PartPose.offset(6.0F, 0.0F, -8.9F));
        PartDefinition modelPartData10 = modelPartData9.addOrReplaceChild("horn2R", CubeListBuilder.create().texOffs(80, 20).addBox(0.0F, -2.5F, -2.5F, 10.0F, 5.0F, 5.0F),
                PartPose.offset(8.0F, -1.0F, -1.0F));
        modelPartData10.addOrReplaceChild("horn3R", CubeListBuilder.create().texOffs(80, 40).addBox(-1.0F, -2.5F, -2.5F, 9.0F, 4.0F, 4.0F), PartPose.offset(9.0F, 0.4F, 0.0F));
        PartDefinition modelPartData11 = modelPartData1.addOrReplaceChild("legfL", CubeListBuilder.create().texOffs(0, 120).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                PartPose.offset(-10.0F, -3.0F, -11.0F));
        modelPartData11.addOrReplaceChild("thighfLL", CubeListBuilder.create().texOffs(40, 130).addBox(-7.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.3F)), PartPose.offset(3.0F, 0.0F, 0.0F));
        PartDefinition modelPartData12 = modelPartData1.addOrReplaceChild("legbL", CubeListBuilder.create().texOffs(0, 70).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                PartPose.offset(-10.0F, -3.0F, 13.0F));
        modelPartData12.addOrReplaceChild("thighbL", CubeListBuilder.create().texOffs(40, 90).addBox(-8.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.3F)), PartPose.offset(4.0F, 0.0F, 0.0F));
        PartDefinition modelPartData13 = modelPartData1.addOrReplaceChild("legfR", CubeListBuilder.create().texOffs(0, 120).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F),
                PartPose.offset(10.0F, -3.0F, -11.0F));
        modelPartData13.addOrReplaceChild("thighfLR", CubeListBuilder.create().texOffs(40, 130).addBox(0.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.3F)), PartPose.offset(-4.0F, 0.0F, 0.0F));
        PartDefinition modelPartData14 = modelPartData1.addOrReplaceChild("legbR", CubeListBuilder.create().texOffs(0, 70).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 24.0F, 9.0F), PartPose.offset(10.0F, -3.0F, 13.0F));
        modelPartData14.addOrReplaceChild("thighbR", CubeListBuilder.create().texOffs(40, 90).addBox(0.0F, 0.0F, -4.5F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.3F)), PartPose.offset(-4.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }


    @Override
    public void setupAnim(EnderwarthogRenderState state) {
        float limbAngle = state.walkAnimationPos;
        float limbDistance = state.walkAnimationSpeed;
        float animationProgress = state.ageInTicks;
        float headYaw = state.yRot;
        float headPitch = state.xRot;

        this.head.xRot = 0.2602F + headPitch * 0.010453292F;
        this.neck.yRot = headYaw * 0.004453292F;
        this.head.yRot = headYaw * 0.005453292F;
        this.jaw.xRot = 0.0524F;
        this.ribbon1L.xRot = 0.7854F;
        this.ribbon1L.yRot = -0.4712F;
        this.ribbon2L.xRot = -0.3491F;
        this.ribbon2L.yRot = 0.2443F;
        this.ribbon1R.xRot = 0.7854F;
        this.ribbon1R.yRot = 0.4712F;
        this.ribbon2R.xRot = -0.3491F;
        this.ribbon2R.yRot = -0.2443F;
        this.horn1L.xRot = 0.1022F;
        this.horn1L.yRot = -0.2274F;
        this.horn1L.zRot = -0.8633F;
        this.horn2L.xRot = -0.0524F;
        this.horn2L.yRot = -0.5411F;
        this.horn2L.zRot = -0.3142F;
        this.horn3L.xRot = 0.2269F;
        this.horn3L.yRot = -0.576F;
        this.horn3L.zRot = -0.2793F;
        this.horn1R.xRot = -0.1047F;
        this.horn1R.yRot = 0.3142F;
        this.horn1R.zRot = 0.8727F;
        this.horn2R.xRot = 0.0524F;
        this.horn2R.yRot = 0.5411F;
        this.horn2R.zRot = 0.3142F;
        this.horn3R.xRot = 0.2269F;
        this.horn3R.yRot = 0.576F;
        this.horn3R.zRot = 0.2793F;

        this.legbR.xRot = Mth.cos(limbAngle * 0.6662F) * 0.8F * limbDistance;
        this.legbL.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 0.8F * limbDistance;
        this.legfR.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * 0.8F * limbDistance;
        this.legfL.xRot = Mth.cos(limbAngle * 0.6662F) * 0.8F * limbDistance;

        float k = Mth.sin(state.attackTime * 3.1415927F);

        if (k > 0.0F) {
            if (state.biteAttack) {
                this.neck.xRot = -k * 0.05F;
                this.head.xRot = -k * 0.01F;
                this.jaw.xRot = 0.0524F + k * 0.45F;
            } else {
                this.neck.xRot = -k * 0.4F;
                this.head.xRot = -k * 0.2F;
            }
        } else {
            this.neck.xRot = 0.0F;
        }
    }

}
