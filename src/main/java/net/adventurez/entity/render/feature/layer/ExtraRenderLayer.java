package net.adventurez.entity.render.feature.layer;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

// import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;

public class ExtraRenderLayer {

  //Old Way

  // protected static final RenderPhase.Transparency GLOWING_TRANSPARENCY = new RenderPhase.Transparency(
  //     "glowing_transparency", () -> {
  //       RenderSystem.enableBlend();
  //       RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
  //     }, () -> {
  //       RenderSystem.disableBlend();
  //       RenderSystem.defaultBlendFunc();
  //     });

  // public static final RenderLayer getGlowing(String string) {
  //   return RenderLayer.of("entity_glowing", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256,
  //       RenderLayer.MultiPhaseParameters.builder()
  //           .texture(new RenderPhase.Texture(new Identifier(string), false, false)).transparency(GLOWING_TRANSPARENCY)
  //           .build(true));
  // }

  protected static final RenderPhase.Transparency NO_TRANSPARENCY = new RenderPhase.Transparency("no_transparency",
      () -> {
        RenderSystem.disableBlend();
      }, () -> {
      });

  public static RenderLayer getGlowing(String string) {
    RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
        .texture(new RenderPhase.Texture(new Identifier(string), false, false)).transparency(NO_TRANSPARENCY)
        .diffuseLighting(new RenderPhase.DiffuseLighting(true)).alpha(new RenderPhase.Alpha(0.003921569F))
        .overlay(new RenderPhase.Overlay(true)).build(true);
    return RenderLayer.of("entity_glowing", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256, true,
        false, multiPhaseParameters);
  }

}