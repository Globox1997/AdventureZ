package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import java.util.Random;

public class ParticleInit {

    public static final SimpleParticleType AMETHYST_SHARD_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType VOID_CLOUD_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType SPRINT_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType FART_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, AdventureMain.identifierOf("amethyst_shard_particle"), AMETHYST_SHARD_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, AdventureMain.identifierOf("void_cloud_particle"), VOID_CLOUD_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, AdventureMain.identifierOf("sprint_particle"), SPRINT_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, AdventureMain.identifierOf("fart_particle"), FART_PARTICLE);
    }

    @Environment(EnvType.CLIENT)
    public static class FartParticle extends ExplodeParticle {

        public FartParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, SpriteSet spriteProvider) {
            super(clientWorld, d, e, f, g, h, i, spriteProvider);
            this.gravity = 0.5F;
            this.rCol = 0f;
            this.gCol = 0f;
            this.bCol = 0f;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet spriteProvider;

            public Factory(SpriteSet spriteProvider) {
                this.spriteProvider = spriteProvider;
            }

            @Override
            public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new FartParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ShardParticle extends SingleQuadParticle {
        static final Random RANDOM = new Random();
        private final SpriteSet spriteProvider;

        public ShardParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, SpriteSet spriteProvider) {
            super(clientWorld, d, e, f, g, h, i, spriteProvider.first());
            this.friction = 0.96F;
            this.speedUpWhenYMotionIsBlocked = true;
            this.spriteProvider = spriteProvider;
            this.quadSize *= 0.75F;
            this.hasPhysics = false;
            this.setSpriteFromAge(spriteProvider);
        }

        @Override
        public SingleQuadParticle.Layer getLayer() {
            return SingleQuadParticle.Layer.TRANSLUCENT;
        }

        @Override
        public int getLightCoords(float tint) {
            float f = ((float) this.age + tint) / (float) this.lifetime;
            f = Mth.clamp(f, 0.0F, 1.0F);
            int i = super.getLightCoords(tint);
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
            this.setSpriteFromAge(this.spriteProvider);
        }

        @Environment(EnvType.CLIENT)
        public static class ShardFactory implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet spriteProvider;

            public ShardFactory(SpriteSet sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                ShardParticle shardParticle = new ShardParticle(clientWorld, d, e, f, 0.5D - ShardParticle.RANDOM.nextDouble(), h, 0.5D - ShardParticle.RANDOM.nextDouble(), this.spriteProvider);
                int rand = clientWorld.getRandom().nextInt(4);
                if (rand == 0)
                    shardParticle.setColor(1.0F, 0.796F, 0.9F);
                else if (rand == 1)
                    shardParticle.setColor(0.392F, 0.278F, 0.619F);
                else if (rand == 2)
                    shardParticle.setColor(0.65F, 0.47F, 0.945F);
                else if (rand == 3)
                    shardParticle.setColor(0.784F, 0.564F, 0.941F);

                shardParticle.yd *= 0.20000000298023224D;
                if (g == 0.0D && i == 0.0D) {
                    shardParticle.xd *= 0.10000000149011612D;
                    shardParticle.zd *= 0.10000000149011612D;
                }

                shardParticle.setLifetime((int) (8.0D / (clientWorld.getRandom().nextDouble() * 0.8D + 0.2D)));
                return shardParticle;
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static class VoidCloudParticle extends SingleQuadParticle {
        private final double startX;
        private final double startY;
        private final double startZ;

        private VoidCloudParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, SpriteSet spriteProvider, RandomSource random) {
            super(clientWorld, d, e, f, spriteProvider.get(random));
            this.xd = g;
            this.yd = h;
            this.zd = i;
            this.x = d;
            this.y = e;
            this.z = f;
            this.startX = this.x;
            this.startY = this.y;
            this.startZ = this.z;
            this.quadSize = 0.5F * (this.random.nextFloat() * 0.05F + 0.4F);
            this.rCol = this.random.nextFloat() * 0.2F;
            this.gCol = this.random.nextFloat() * 0.2F;
            this.bCol = this.random.nextFloat() * 0.2F;
            this.lifetime = (int) (this.random.nextFloat() * 2.0F) + 10;
        }

        @Override
        public SingleQuadParticle.Layer getLayer() {
            return SingleQuadParticle.Layer.OPAQUE;
        }

        @Override
        public void move(double dx, double dy, double dz) {
            this.setBoundingBox(this.getBoundingBox().move(dx, dy, dz));
            this.setLocationFromBoundingbox();
        }

        @Override
        public float getQuadSize(float tickDelta) {
            float f = ((float) this.age + tickDelta) / (float) this.lifetime;
            f = 1.0F - f;
            f *= f;
            f = 1.0F - f;
            return this.quadSize * f;
        }

        @Override
        public int getLightCoords(float tint) {
            int i = super.getLightCoords(tint);
            float f = (float) this.age / (float) this.lifetime;
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
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ >= this.lifetime) {
                this.remove();
            } else {
                float f = (float) this.age / (float) this.lifetime;
                float g = f;
                f = -f + f * f * 2.0F;
                f = 1.0F - f;
                this.x = this.startX + this.xd * (double) f;
                this.y = this.startY + this.yd * (double) f + (double) (1.0F - g);
                this.z = this.startZ + this.zd * (double) f;
            }
        }

        @Environment(EnvType.CLIENT)
        public static class CloudFactory implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet spriteProvider;

            public CloudFactory(SpriteSet spriteProvider) {
                this.spriteProvider = spriteProvider;
            }

            @Override
            public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new VoidCloudParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider, random);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SprintParticle extends SingleQuadParticle {
        static final Random RANDOM = new Random();
        private final SpriteSet spriteProvider;

        public SprintParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, SpriteSet spriteProvider) {
            super(clientWorld, d, e, f, spriteProvider.first());
            this.friction = 1.0F;
            this.speedUpWhenYMotionIsBlocked = false;
            this.gravity = 0.0F;
            this.spriteProvider = spriteProvider;
            this.hasPhysics = false;
            this.setSpriteFromAge(spriteProvider);
        }

        @Override
        public SingleQuadParticle.Layer getLayer() {
            return SingleQuadParticle.Layer.TRANSLUCENT;
        }

        @Override
        public void tick() {
            super.tick();
            this.setSpriteFromAge(this.spriteProvider);
        }

        @Environment(EnvType.CLIENT)
        public static class SprintFactory implements ParticleProvider<SimpleParticleType> {
            private final SpriteSet spriteProvider;

            public SprintFactory(SpriteSet sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                SprintParticle sprintParticle = new SprintParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
                sprintParticle.xd = -g * 1.5D;
                sprintParticle.zd = -i * 1.5D;
                sprintParticle.setLifetime(8);
                return sprintParticle;
            }
        }

    }
}
