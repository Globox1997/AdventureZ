package net.adventurez.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.adventurez.AdventureMain;
import net.adventurez.entity.model.RockModel;
import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ItemSupplier;

@Environment(EnvType.CLIENT)
public class ThrownRockRenderer extends EntityRenderer<ThrownRockEntity> {
    private static final ResourceLocation GOLEM_TEXTURE = AdventureMain.identifierOf("textures/entity/thrown_golem_rock.png");
    private static final ResourceLocation VOID_TEXTURE = AdventureMain.identifierOf("textures/entity/thrown_void_rock.png");
    private final RockModel model = new RockModel(RockModel.getTexturedModelData().bakeRoot());

    public ThrownRockRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public int getBlockLightLevel(ThrownRockEntity ThrownRockEntity, BlockPos blockPos) {
        return ThrownRockEntity.level().getMaxLocalRawBrightness(blockPos);
    }

    @Override
    public void render(ThrownRockEntity thrownRockEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(this.getTextureLocation(thrownRockEntity)));
        model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        matrixStack.popPose();
        super.render(thrownRockEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownRockEntity thrownRockEntity) {
        if (((ItemSupplier) thrownRockEntity).getItem().getItem() == thrownRockEntity.getDefaultItem()) {
            return GOLEM_TEXTURE;
        } else
            return VOID_TEXTURE;
    }
}
