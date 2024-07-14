package rus.iglo.MirseEngine.Engine.Function;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rus.iglo.MirseEngine.Engine.Action;
import rus.iglo.MirseEngine.Engine.Distanse;
import rus.iglo.MirseEngine.Engine.npc.ModEntityTypes;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;

import static rus.iglo.MirseEngine.MirseEngine.serverPlayer;

public class NPCJAVA {
    public static NPCEntity npcEntity;
    private int x, y, z;
    private String textures;
    private String name;
    public NPCJAVA(int x, int y, int z, String textures, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.textures = textures;
        this.name = name;
    }
    public NPCJAVA create() {
        try {
            Level level = serverPlayer.getLevel();
            npcEntity = new NPCEntity(ModEntityTypes.NPC.get(), level);
            npcEntity.moveTo(x, y, z);
            npcEntity.setTexture(textures);
            npcEntity.setCustomName(Component.literal(name));
            npcEntity.setCustomNameVisible(false);
            level.addFreshEntity(npcEntity);
            return this;
        } catch (Exception e) {
            serverPlayer.sendSystemMessage(Component.literal("ERROR: Произошла ошибка при вызове NPC: " + e.getMessage()));
            return null;
        }
    }
    public void delete() {
        if (npcEntity != null) {
            npcEntity.discard();
        } else {
            serverPlayer.sendSystemMessage(Component.literal(" значение NPC-объекта равно нулю."));
        }
    }

    public void kill() {
        if (npcEntity != null) {
            npcEntity.kill();
        } else {
            serverPlayer.sendSystemMessage(Component.literal("ERROR: Сущность NPC равна нулю."));
        }
    }
    static double xx;
    static double yy;
    static double zz;
    static double speeed;
    static double xnpc;
    static double ynpc;
    static double znpc;

    public void moveToPosition(int x, int y, int z, int speed) {
        npcEntity.goalSelector.addGoal(1, new MovePlayerEntity(npcEntity, x, y, z, speed));
    }
    public void moveToPosition(int x, int y, int z) {
        npcEntity.goalSelector.addGoal(1, new MovePlayerEntity(npcEntity, x, y, z, 1));
    }
    public void setHeadRotation(double x,double y,double z) {
        npcEntity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x,y,z));
    }
    public void setHealPlayer() {
        NPCEntity.t++;
        if (NPCEntity.t == 2) {
            NPCEntity.t = 0;
        }
    }
    public void playAnim(String anim) {
        npcEntity.setPlayAnim(anim);
    }

    public void stopAnim() {
        npcEntity.setPlayAnim("");
    }

    public void setGeo(String geo) {
        npcEntity.setGeo(geo);
    }

    public void setTextures(String textures) {
        npcEntity.setTexture(textures);
    }
    public void setAnimIdle(String animIdle) {
        npcEntity.setAnimIdle(animIdle);
    }

    public void setAnimWalk(String animWalk) {
        npcEntity.setAnimWalk(animWalk);
    }

    public void setName(String name) {
        npcEntity.setCustomName(Component.nullToEmpty(name));
    }

    public void teleport(int x, int y, int z) {
        npcEntity.teleportTo(x, y, z);
    }
    public double getX() {
        return npcEntity.getX();
    }
    public double getY() {
        return npcEntity.getY();
    }
    public double getZ() {
        return npcEntity.getZ();
    }
    public void performActionBeforeDistance(Action action, int d) {
        Distanse.setRot(() -> {
            action.execute();
        },d);
    }
    public void sendMessage(String text) {
        serverPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "[" + name + "] " + ChatFormatting.RESET + text));
    }
    public class MovePlayerEntity extends Goal {

        protected final NPCEntity npcEntity;
        protected double speed;
        protected double x;
        protected double y;
        protected double z;

        public MovePlayerEntity(NPCEntity npcEntity, int x, int y, int z, double speed) {
            this.npcEntity = npcEntity;
            this.speed = speed;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public boolean canUse() {
            return true;
        }

        public void start() {
            this.npcEntity.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
        }

        public boolean canContinueToUse() {
            if (npcEntity.getX() == x &&  npcEntity.getY() == y && npcEntity.getY() == z){
                for (WrappedGoal availableGoal : npcEntity.goalSelector.getAvailableGoals()) {
                    if (availableGoal.getGoal().toString().equals("MovePlayerEntity")){
                        npcEntity.goalSelector.removeGoal(availableGoal.getGoal());
                    }
                }
            }
            return !this.npcEntity.getNavigation().isDone();
        }
    }
}
