package net.adventurez.entity.render.feature.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class FadingRenderLayer {
  protected static final RenderPhase.Transparency GLOWING_TRANSPARENCY = new RenderPhase.Transparency(
      "fading_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
      }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
      });

  public static final RenderLayer getFading(String string) {
    return RenderLayer.of("entity_fading", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256,
        RenderLayer.MultiPhaseParameters.builder()
            .texture(new RenderPhase.Texture(new Identifier(string), false, false)).transparency(GLOWING_TRANSPARENCY)
            .lightmap(new RenderPhase.Lightmap(true)).build(true));
  }

}
