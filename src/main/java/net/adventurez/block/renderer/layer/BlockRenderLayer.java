package net.adventurez.block.renderer.layer;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class BlockRenderLayer {
  protected static final RenderPhase.Transparency NO_TRANSPARENCY = new RenderPhase.Transparency("no_transparency",
      () -> {
        RenderSystem.disableBlend();
      }, () -> {
      });

  public static final RenderLayer getNormal(String string) {
    return RenderLayer.of("entity_cutout_no_effect", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, 7, 256,
        RenderLayer.MultiPhaseParameters.builder()
            .texture(new RenderPhase.Texture(new Identifier(string), false, false)).transparency(NO_TRANSPARENCY)
            .lightmap(new RenderPhase.Lightmap(true)).overlay(new RenderPhase.Overlay(true))
            .cull(new RenderPhase.Cull(false)).alpha(new RenderPhase.Alpha(0.003921569F)).build(true));
  }

}
