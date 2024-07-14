package rus.iglo.MirseEngine.Engine.npc.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import rus.iglo.MirseEngine.Engine.npc.ModEntityTypes;
import rus.iglo.MirseEngine.Engine.npc.client.NPCRenderer;
import rus.iglo.MirseEngine.MirseEngine;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NPCEntity extends Mob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> GEO = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIM = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> PLAY_ANIM = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIM_IDLE = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIM_WALK = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Component> CUSTOM_NAME = SynchedEntityData.defineId(NPCEntity.class, EntityDataSerializers.COMPONENT);

    public NPCEntity(EntityType<? extends NPCEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0D)
                .add(Attributes.ATTACK_SPEED, 0.3f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(getAnimWalk(), true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(getAnimIdle(), true));
        return PlayState.CONTINUE;
    }

    private PlayState playanim(AnimationEvent event) {
            if (!this.getAnim().equals("")) {
                event.getController().setAnimation((new AnimationBuilder()).addAnimation(this.getAnim()));
                return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE, "npc.png");
        this.entityData.define(GEO, "npc.geo.json");
        this.entityData.define(PLAY_ANIM, "");
        this.entityData.define(ANIM, "npc.story.json");
        this.entityData.define(ANIM_IDLE, "story.npc.idle");
        this.entityData.define(ANIM_WALK, "story.npc.walk");
        this.entityData.define(CUSTOM_NAME, Component.nullToEmpty(""));
    }

    public String getAnimWalk() {
        return this.entityData.get(ANIM_WALK);
    }

    public void setAnimWalk(String anim) {
        this.entityData.set(ANIM_WALK, anim);
    }

    public String getAnimIdle() {
        return this.entityData.get(ANIM_IDLE);
    }

    public void setAnimIdle(String anim) {
        this.entityData.set(ANIM_IDLE, anim);
    }

    public String getAnimAnim() {
        return this.entityData.get(ANIM);
    }

    public void setAnim(String anim) {
        this.entityData.set(ANIM, anim);
    }

    public String getAnim() {
        return this.entityData.get(PLAY_ANIM);
    }

    public void setPlayAnim(String anim) {
            this.entityData.set(PLAY_ANIM, anim);
    }

    public void setGeo(String geo) {
        this.entityData.set(GEO, geo);
    }

    public String getGeo() {
        return this.entityData.get(GEO);
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    public static int sm = 0;

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController(this, "playanim", 0, this::playanim));
    }

    @Override
    public void tick() {
        super.tick();
        if (t == 0) {
            this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6.0F));
        }
    }
    public static int t = 1;

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    @Mod.EventBusSubscriber(modid = MirseEngine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.NPC.get(), NPCRenderer::new);
        }
    }
    @Mod.EventBusSubscriber
    public class t {
        @SubscribeEvent
        public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                ItemStack heldItem = serverPlayer.getMainHandItem();
                if (heldItem.getItem() == Items.PUFFERFISH) {
                    if (event.getTarget() instanceof Mob mob) {
                        mob.discard();
                    }
                }
            }
        }
    }
}
