package net.adventurez.entity.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DragonRenderState extends AdventureRenderState {
    public float partialTick;
    public boolean fireBreath;
    public boolean clientStartFlying;
    public boolean flying;
    public boolean clientEndFlying;
    public float wingOpen;
    public float flapPhase;
    public boolean onGround;
    public boolean landingWings;
    public float landing;
    public boolean sittingPose;
    public boolean vehicle;
    public boolean yawning;
    public float yawnPitch;
    public boolean hasSaddle;
    public boolean hasChest;
    public boolean otherEars;
    public boolean otherTail;
    public boolean otherEyes;
    public boolean tamed;
    public boolean redDragon;
    public int size;
}
