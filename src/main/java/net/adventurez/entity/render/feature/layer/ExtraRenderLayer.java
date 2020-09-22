package net.adventurez.entity.render.feature.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

// import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;

public class ExtraRenderLayer {
  protected static final RenderPhase.Transparency GLOWING_TRANSPARENCY = new RenderPhase.Transparency(
      "glowing_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
      }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
      });

  public static final RenderLayer getGlowing(String string) {
    return RenderLayer.of("entity_glowing", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256,
        RenderLayer.MultiPhaseParameters.builder()
            .texture(new RenderPhase.Texture(new Identifier(string), false, false)).transparency(GLOWING_TRANSPARENCY)
            .build(true));
  }

}
