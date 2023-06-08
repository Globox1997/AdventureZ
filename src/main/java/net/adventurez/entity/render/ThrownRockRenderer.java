package net.adventurez.entity.render;

import net.adventurez.entity.model.RockModel;
import net.adventurez.entity.nonliving.ThrownRockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ThrownRockRenderer extends EntityRenderer<ThrownRockEntity> {
    private static final Identifier GOLEM_TEXTURE = new Identifier("adventurez:textures/entity/thrown_golem_rock.png");
    private static final Identifier VOID_TEXTURE = new Identifier("adventurez:textures/entity/thrown_void_rock.png");
    private final RockModel model = new RockModel(RockModel.getTexturedModelData().createModel());

    public ThrownRockRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public int getBlockLight(ThrownRockEntity ThrownRockEntity, BlockPos blockPos) {
        return ThrownRockEntity.getWorld().getLightLevel(blockPos);
    }

    @Override
    public void render(ThrownRockEntity thrownRockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(thrownRockEntity)));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        matrixStack.pop();
        super.render(thrownRockEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ThrownRockEntity thrownRockEntity) {
        if (((FlyingItemEntity) thrownRockEntity).getStack().getItem() == thrownRockEntity.getDefaultItem()) {
            return GOLEM_TEXTURE;
        } else
            return VOID_TEXTURE;
    }
}