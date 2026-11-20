package net.adventurez.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.adventurez.AdventureMain;
import net.adventurez.block.PiglinFlag;
import net.adventurez.block.entity.PiglinFlagEntity;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PiglinFlagRenderer implements BlockEntityRenderer<PiglinFlagEntity, PiglinFlagRenderer.PiglinFlagRenderState> {

    private static final Identifier TEXTURE = AdventureMain.identifierOf("textures/block/piglin_flag.png");
    private final FlagModel model;

    public PiglinFlagRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new FlagModel(ctx.bakeLayer(RenderInit.PIGLIN_FLAG_LAYER));
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition flag = modelPartData.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(28, 25).addBox(-4.0F, 7.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -15.0F, 0.0F, 4.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(28, 39).addBox(-3.0F, -10.0F, 1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 9.0F, 2.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cloth = flag.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 0.0F, -7.0F, 0.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -10.0F, 12.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public PiglinFlagRenderState createRenderState() {
        return new PiglinFlagRenderState();
    }

    @Override
    public void extractRenderState(PiglinFlagEntity entity, PiglinFlagRenderState state, float tickDelta, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, tickDelta, cameraPosition, breakProgress);
        BlockState blockState = entity.getBlockState();
        state.isFlag = blockState.getBlock() instanceof PiglinFlag;
        state.direction = state.isFlag ? blockState.getValue(HorizontalDirectionalBlock.FACING) : Direction.NORTH;
        BlockPos blockPos = entity.getBlockPos();
        long worldTime = entity.getLevel().getGameTime();
        float goodFloat = ((float) Math.floorMod((long) (blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13) + worldTime, 100L) + tickDelta) / 100.0F;
        state.clothRotation = Mth.cos(6.2831855F * goodFloat) / 4;
    }

    @Override
    public void submit(PiglinFlagRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        matrices.pushPose();
        Direction blockDirection = state.direction;
        if (state.isFlag) {
            if (blockDirection.equals(Direction.NORTH)) {
            } else if (blockDirection.equals(Direction.EAST)) {
                matrices.translate(1D, 0D, 0D);
                matrices.rotate(Axis.YP.rotationDegrees(270F));
            } else if (blockDirection.equals(Direction.SOUTH)) {
                matrices.translate(1D, 0D, 1D);
                matrices.rotate(Axis.YP.rotationDegrees(180F));
            } else if (blockDirection.equals(Direction.WEST)) {
                matrices.translate(0D, 0D, 1D);
                matrices.rotate(Axis.YP.rotationDegrees(90F));
            }
        }
        matrices.translate(0.5D, 0.5D, 0.5D);
        submitNodeCollector.submitModel(this.model, state.clothRotation, matrices, RenderTypes.entityCutout(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

    @Environment(EnvType.CLIENT)
    public static class PiglinFlagRenderState extends BlockEntityRenderState {
        public boolean isFlag;
        public Direction direction = Direction.NORTH;
        public float clothRotation;
    }

    @Environment(EnvType.CLIENT)
    private static class FlagModel extends Model<Float> {
        private final ModelPart cloth;

        public FlagModel(ModelPart root) {
            super(root, RenderTypes::entityCutout);
            this.cloth = root.getChild("flag").getChild("cloth");
        }

        @Override
        public void setupAnim(Float clothRotation) {
            this.cloth.zRot = clothRotation;
        }
    }
}
