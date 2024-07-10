package rus.iglo.MirseEngine.Engine.npc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;
import rus.iglo.MirseEngine.MirseEngine;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MirseEngine.MODID);
    public static final RegistryObject<EntityType<NPCEntity>> NPC =
            ENTITY_TYPES.register("npc",
                    () -> EntityType.Builder.of(NPCEntity::new, MobCategory.MONSTER)

                            // p
                            .sized(0.5f, 1.7f)
                            .build(new ResourceLocation("npc").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
