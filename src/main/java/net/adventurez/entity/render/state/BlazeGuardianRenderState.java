package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BlazeGuardianRenderState extends AdventureRenderState {
    public boolean shieldEast;
    public boolean shieldNorth;
    public boolean shieldSouth;
    public boolean shieldWest;
}
