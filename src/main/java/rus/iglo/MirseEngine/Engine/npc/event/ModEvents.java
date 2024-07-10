package rus.iglo.MirseEngine.Engine.npc.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rus.iglo.MirseEngine.Engine.npc.ModEntityTypes;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;
import rus.iglo.MirseEngine.MirseEngine;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MirseEngine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.NPC.get(), NPCEntity.setAttributes());
        }
    }
}
