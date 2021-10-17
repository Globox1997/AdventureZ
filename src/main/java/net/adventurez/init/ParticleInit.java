package net.adventurez.init;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;

public class ParticleInit {

    public static final DefaultParticleType AMETHYST_SHARD_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType VOID_CLOUD_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("adventurez", "amethyst_shard_particle"), AMETHYST_SHARD_PARTICLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("adventurez", "void_cloud_particle"), VOID_CLOUD_PARTICLE);
    }

    @Environment(EnvType.CLIENT)
    static class ShardParticle extends SpriteBillboardParticle {
        static final Random RANDOM = new Random();
        private final SpriteProvider spriteProvider;

        public ShardParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
            super(clientWorld, d, e, f, g, h, i);
            this.field_28786 = 0.96F;
            this.field_28787 = true;
            this.spriteProvider = spriteProvider;
            this.scale *= 0.75F;
            this.collidesWithWorld = false;
            this.setSpriteForAge(spriteProvider);
        }

        @Override
        public ParticleTextureSheet getType() {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }

        @Override
        public int getBrightness(float tint) {
            float f = ((float) this.age + tint) / (float) this.maxAge;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            int i = super.getBrightness(tint);
            int j = i & 255;
            int k = i >> 16 & 255;
            j += (int) (f * 15.0F * 16.0F);
            if (j > 240) {
                j = 240;
            }

            return j | k << 16;
        }

        @Override
        public void tick() {
            super.tick();
            this.setSpriteForAge(this.spriteProvider);
        }

        @Environment(EnvType.CLIENT)
        public static class ShardFactory implements ParticleFactory<DefaultParticleType> {
            private final SpriteProvider spriteProvider;

            public ShardFactory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
                ShardParticle shardParticle = new ShardParticle(clientWorld, d, e, f, 0.5D - ShardParticle.RANDOM.nextDouble(), h, 0.5D - ShardParticle.RANDOM.nextDouble(), this.spriteProvider);
                int rand = clientWorld.random.nextInt(4);
                if (rand == 0)
                    shardParticle.setColor(1.0F, 0.796F, 0.9F);
                else if (rand == 1)
                    shardParticle.setColor(0.392F, 0.278F, 0.619F);
                else if (rand == 2)
                    shardParticle.setColor(0.65F, 0.47F, 0.945F);
                else if (rand == 3)
                    shardParticle.setColor(0.784F, 0.564F, 0.941F);

                shardParticle.velocityY *= 0.20000000298023224D;
                if (g == 0.0D && i == 0.0D) {
                    shardParticle.velocityX *= 0.10000000149011612D;
                    shardParticle.velocityZ *= 0.10000000149011612D;
                }

                shardParticle.setMaxAge((int) (8.0D / (clientWorld.random.nextDouble() * 0.8D + 0.2D)));
                return shardParticle;
            }
        }

    }

    @Environment(EnvType.CLIENT)
    static class VoidCloudParticle extends SpriteBillboardParticle {
        private final double startX;
        private final double startY;
        private final double startZ;

        private VoidCloudParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            super(clientWorld, d, e, f);
            this.velocityX = g;
            this.velocityY = h;
            this.velocityZ = i;
            this.x = d;
            this.y = e;
            this.z = f;
            this.startX = this.x;
            this.startY = this.y;
            this.startZ = this.z;
            this.scale = 0.5F * (this.random.nextFloat() * 0.05F + 0.4F);
            this.colorRed = this.random.nextFloat() * 0.2F;
            this.colorGreen = this.random.nextFloat() * 0.2F;
            this.colorBlue = this.random.nextFloat() * 0.2F;
            this.maxAge = (int) (this.random.nextFloat() * 2.0F) + 10;
        }

        @Override
        public ParticleTextureSheet getType() {
            return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
        }

        @Override
        public void move(double dx, double dy, double dz) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
        }

        @Override
        public float getSize(float tickDelta) {
            float f = ((float) this.age + tickDelta) / (float) this.maxAge;
            f = 1.0F - f;
            f *= f;
            f = 1.0F - f;
            return this.scale * f;
        }

        @Override
        public int getBrightness(float tint) {
            int i = super.getBrightness(tint);
            float f = (float) this.age / (float) this.maxAge;
            f *= f;
            f *= f;
            int j = i & 255;
            int k = i >> 16 & 255;
            k += (int) (f * 15.0F * 16.0F);
            if (k > 240) {
                k = 240;
            }

            return j | k << 16;
        }

        @Override
        public void tick() {
            this.prevPosX = this.x;
            this.prevPosY = this.y;
            this.prevPosZ = this.z;
            if (this.age++ >= this.maxAge) {
                this.markDead();
            } else {
                float f = (float) this.age / (float) this.maxAge;
                float g = f;
                f = -f + f * f * 2.0F;
                f = 1.0F - f;
                this.x = this.startX + this.velocityX * (double) f;
                this.y = this.startY + this.velocityY * (double) f + (double) (1.0F - g);
                this.z = this.startZ + this.velocityZ * (double) f;
            }
        }

        @Environment(EnvType.CLIENT)
        public static class CloudFactory implements ParticleFactory<DefaultParticleType> {
            private final SpriteProvider spriteProvider;

            public CloudFactory(SpriteProvider spriteProvider) {
                this.spriteProvider = spriteProvider;
            }

            @Override
            public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
                VoidCloudParticle portalParticle = new VoidCloudParticle(clientWorld, d, e, f, g, h, i);
                portalParticle.setSprite(this.spriteProvider);
                return portalParticle;
            }
        }
    }
}
