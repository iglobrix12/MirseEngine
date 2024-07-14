package rus.iglo.MirseEngine.Engine;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;
import rus.iglo.MirseEngine.MirseEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = MirseEngine.MODID)
public class Distanse {
    private static int DISTANCE_THRESHOLD = 0;
    private static List<Action> actionArrayList = new ArrayList<>();

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                player.level.getEntities(player, player.getBoundingBox().inflate(DISTANCE_THRESHOLD), entity -> {
                    if (entity instanceof NPCEntity) {
                        NPCEntity npc = (NPCEntity) entity;
                        double distance = player.distanceToSqr(npc);
                        if (distance <= DISTANCE_THRESHOLD * DISTANCE_THRESHOLD) {
                            executeAction();
                        }
                        return true;
                    }
                    return false;
                });
            }
        }
    }

    public static void setRot(Action action,int d) {
        actionArrayList.add(action);
        DISTANCE_THRESHOLD = d;
    }

    private static void executeAction() {
        Iterator<Action> iterator = actionArrayList.iterator();
        while (iterator.hasNext()) {
            Action action = iterator.next();
            action.run();
            iterator.remove();
        }
    }
}