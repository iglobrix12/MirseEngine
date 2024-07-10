package rus.iglo.MirseEngine.Engine;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class ChatEvents {
    public static Map<String, Runnable> taskMap = new HashMap<>();

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        String message = event.getMessage();
        if (taskMap.containsKey(message)) {
            taskMap.get(message).run();
            event.setCanceled(true); // Отменяем отправку оригинального сообщения
        }
    }
}