package rus.iglo.MirseEngine.Engine;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import rus.iglo.MirseEngine.MirseEngine;

public class Camera {
    private static double originalX;
    private static double originalY;
    private static double originalZ;
    private static double targetX;
    private static double targetY;
    private static double targetZ;
    private static double currentX;
    private static double currentY;
    private static double currentZ;
    private static double currentxx;
    private static double currentyy;
    private static double currentzz;
    private static double getPlayerX;
    private static double getPlayery;
    private static double getPlayerz;
    private static double speed;
    private static double stack = 0;
    private static GameType originalGameMode;

    @SubscribeEvent
    public static void tickevent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (stack == 1) {
                updateCamera();
            }
        }
    }

    public void setCamera(double startX, double startY, double startZ, double endX, double endY, double endZ, double currentxx, double currentyy, double currentzz, double speed) {
        try {
            if (MirseEngine.serverPlayer == null) {
                throw new IllegalStateException("Игрок не изцелезирован");
            }

            this.currentxx = currentxx;
            this.currentyy = currentyy;
            this.currentzz = currentzz;
            this.getPlayerX = MirseEngine.serverPlayer.getX();
            this.getPlayery = MirseEngine.serverPlayer.getY();
            this.getPlayerz = MirseEngine.serverPlayer.getZ();
            this.originalX = startX;
            this.originalY = startY;
            this.originalZ = startZ;
            this.targetX = endX;
            this.targetY = endY;
            this.targetZ = endZ;
            this.currentX = startX;
            this.currentY = startY;
            this.currentZ = startZ;
            this.speed = speed;
            this.stack = 1;

            originalGameMode = MirseEngine.serverPlayer.gameMode.getGameModeForPlayer();
            MirseEngine.serverPlayer.setGameMode(GameType.SPECTATOR);
        } catch (Exception e) {
            MirseEngine.function.sendMessage("Error", "При настройке камеры произошла ошибка: " + e.getMessage());
        }
    }

    public void setCamera(double startX, double startY, double startZ, double endX, double endY, double endZ, double speed) {
        try {
            if (MirseEngine.serverPlayer == null) {
                throw new IllegalStateException("Игрок не инцелизирован");
            }

            this.getPlayerX = MirseEngine.serverPlayer.getX();
            this.getPlayery = MirseEngine.serverPlayer.getY();
            this.getPlayerz = MirseEngine.serverPlayer.getZ();
            this.originalX = startX;
            this.originalY = startY;
            this.originalZ = startZ;
            this.targetX = endX;
            this.targetY = endY;
            this.targetZ = endZ;
            this.currentX = startX;
            this.currentY = startY;
            this.currentZ = startZ;
            this.speed = speed;
            this.stack = 1;
            originalGameMode = MirseEngine.serverPlayer.gameMode.getGameModeForPlayer();
            MirseEngine.serverPlayer.setGameMode(GameType.SPECTATOR);
        } catch (Exception e) {
            MirseEngine.function.sendMessage("Error", "При настройке камеры произошла ошибка: " + e.getMessage());
        }
    }

    public static void updateCamera() {
        if (stack == 1) {
            currentX += (targetX - currentX) * speed;
            currentY += (targetY - currentY) * speed;
            currentZ += (targetZ - currentZ) * speed;

            MirseEngine.serverPlayer.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(currentxx, currentyy, currentzz));
            MirseEngine.serverPlayer.teleportTo(currentX, currentY, currentZ);

            if (Math.abs(currentX - targetX) < speed && Math.abs(currentY - targetY) < speed && Math.abs(currentZ - targetZ) < speed) {
                stack = 0;
            }
        }
    }


    public void resetCamera() {
        try {
            if (MirseEngine.serverPlayer == null) {
                throw new IllegalStateException("Игрок не изцелирован");
            }

            stack = 0;
            MirseEngine.serverPlayer.teleportTo(getPlayerX, getPlayery, getPlayerz);

            MirseEngine.serverPlayer.setGameMode(originalGameMode);
        } catch (Exception e) {
        }
    }
}
