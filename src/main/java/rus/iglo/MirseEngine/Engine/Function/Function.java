package rus.iglo.MirseEngine.Engine.Function;

import com.mojang.math.Vector3d;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import rus.iglo.MirseEngine.MirseEngine;

@Mod.EventBusSubscriber
public class Function {
    public void sendMessage(String name,String text) {
        if (MirseEngine.serverPlayer == null) {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(" "));
        } else {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(ChatFormatting.GREEN + "[" + name + "] " + ChatFormatting.RESET + text));
        }
    }
    public void sendMessage(String text) {
        if (MirseEngine.serverPlayer == null) {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(" "));
        } else {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(text));
        }
    }
    public void sendMessagePlayer(String text) {
        if (MirseEngine.serverPlayer == null) {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(" "));
        } else {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(ChatFormatting.GREEN + "[" + MirseEngine.serverPlayer.getName().getString()  + "] " + ChatFormatting.RESET + text));
        }
    }
    public void teleportPlayer(int x,int y,int z) {
        if (MirseEngine.serverPlayer == null) {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(" "));
        } else {
            MirseEngine.serverPlayer.teleportTo(x,y,z);
        }
    }
    public void killPlayer() {
        if (MirseEngine.serverPlayer == null) {
            MirseEngine.serverPlayer.sendSystemMessage(Component.literal(" "));
        } else {
            MirseEngine.serverPlayer.kill();
        }
    }
}
