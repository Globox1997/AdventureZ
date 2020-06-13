package net.adventurez.init;

import net.adventurez.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {
  public static final EntityType<StoneGolemEntity> STONEGOLEM_ENTITY = FabricEntityTypeBuilder
      .create(SpawnGroup.MONSTER, StoneGolemEntity::new).trackable(74, 2)
      .dimensions(EntityDimensions.fixed(3.36F, 4.44F)).build();

  public static final EntityType<ThrownRockEntity> THROWNROCK_ENTITY = FabricEntityTypeBuilder
      .<ThrownRockEntity>create(SpawnGroup.MISC, ThrownRockEntity::new).trackable(74, 2)
      .dimensions(EntityDimensions.fixed(2.0F, 2.0F)).build();

  public static void init() {
    Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "stone_golem"), STONEGOLEM_ENTITY);
    Registry.register(Registry.ENTITY_TYPE, new Identifier("adventurez", "thrown_rock"), THROWNROCK_ENTITY);

    FabricDefaultAttributeRegistry.register(STONEGOLEM_ENTITY, StoneGolemEntity.createStoneGolemAttributes());
    // Registry.register(Registry.ITEM, new Identifier("golem", "spawn_goli"),
    // new SpawnEggItem(GOLI, 10198167, 6329475, new
    // Item.Settings().maxCount(1).group(ItemGroup.MISC)));
  }
}