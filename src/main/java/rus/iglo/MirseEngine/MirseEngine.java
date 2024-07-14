package rus.iglo.MirseEngine;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import rus.iglo.MirseEngine.Engine.Camera;
import rus.iglo.MirseEngine.Engine.Function.Function;
import rus.iglo.MirseEngine.Engine.Function.SceneJAVA;
import rus.iglo.MirseEngine.Engine.npc.ModEntityTypes;
import rus.iglo.MirseEngine.script.Scripts;
import software.bernie.geckolib3.GeckoLib;

@Mod(MirseEngine.MODID)
public class MirseEngine {

    public static final String MODID = "mirseengine";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Function function = new Function();

    public static SceneJAVA sceneJAVA = new SceneJAVA();

    public static Camera camera = new Camera();

    public static ServerPlayer serverPlayer;

    public MirseEngine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        ModEntityTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
    @Mod.EventBusSubscriber
    public class inzilplayer {
        @SubscribeEvent
        public static void inz(PlayerEvent.PlayerLoggedInEvent event) {
            serverPlayer = (ServerPlayer) event.getEntity();
        }
        @SubscribeEvent
        public static void e(BlockEvent.BreakEvent event) {
            Scripts.execute();
        }
    }
}
