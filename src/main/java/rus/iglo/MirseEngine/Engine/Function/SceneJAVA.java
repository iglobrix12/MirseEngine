package rus.iglo.MirseEngine.Engine.Function;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import rus.iglo.MirseEngine.Engine.Action;
import rus.iglo.MirseEngine.Engine.ChatEvents;
import rus.iglo.MirseEngine.MirseEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static rus.iglo.MirseEngine.MirseEngine.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class SceneJAVA {
    private static final Queue<DelayedTask> taskQueue = new ConcurrentLinkedQueue<>();
    public static void scheduleDelayedTask(Action action, long delay, TimeUnit timeUnit) {
        long delayTicks = timeUnit.toSeconds(delay) * 20;
        taskQueue.add(new DelayedTask(action, delayTicks));
    }
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            DelayedTask currentTask = taskQueue.peek();
            if (currentTask != null) {
                currentTask.tick();
                if (currentTask.isCompleted()) {
                    currentTask.run();
                    taskQueue.poll();
                }
            }
        }
    }
    private static class DelayedTask {
        private final Action action;
        private long delayTicks;

        public DelayedTask(Action action, long delayTicks) {
            this.action = action;
            this.delayTicks = delayTicks;
        }

        public void tick() {
            delayTicks--;
        }

        public boolean isCompleted() {
            return delayTicks <= 0;
        }

        public void run() {
            action.execute();
        }
    }
    public void addScenetimer(Action action,int timer) {
        scheduleDelayedTask(action,timer,TimeUnit.SECONDS);
    }
    public void playScene(Action action) {
        scheduleDelayedTask(action,1, TimeUnit.MILLISECONDS);
    }
    private static final List<Action> list = new ArrayList<>();
    private static int in = 0;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key inputEvent) {
        if (DRINKING_KEY.consumeClick()) {
            if (in < list.size()) {
                Action action = list.get(in);
                action.execute();
                in++;
            }
        }
    }

    public void addSceneKey(Action action) {
        list.add(action);
    }
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(DRINKING_KEY);
        }
    }
    public static final String KEY_CATEGORY_TUTORIAL = "key.logo.StoryCraftTing";
    public static final String KEY_DRINK_WATER = "key.story.playact";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_TUTORIAL);
    public void addChatTask(Action task, String keyword) {
        ChatEvents.taskMap.put(keyword, task);
    }
}
