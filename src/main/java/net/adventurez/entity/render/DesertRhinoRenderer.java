package net.adventurez.entity.render;

import net.adventurez.entity.DesertRhinoEntity;
import net.adventurez.entity.model.DesertRhinoModel;
import net.adventurez.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DesertRhinoRenderer extends MobEntityRenderer<DesertRhinoEntity, DesertRhinoModel<DesertRhinoEntity>> {
    private static final Identifier TEXTURE = new Identifier("adventurez:textures/entity/desert_rhino.png");

    public DesertRhinoRenderer(EntityRendererFactory.Context context) {
        super(context, new DesertRhinoModel<>(context.getPart(RenderInit.DESERT_RHINO_LAYER)), 1.5F);
    }

    // @Override
    // public void scale(DesertRhinoEntity desertRhinoEntity, MatrixStack matrixStack, float f) {
    //     matrixStack.scale(1.3F, 1.3F, 1.3F);
    // }

    @Override
    public Identifier getTexture(DesertRhinoEntity desertRhinoEntity) {
        return TEXTURE;
    }
}
