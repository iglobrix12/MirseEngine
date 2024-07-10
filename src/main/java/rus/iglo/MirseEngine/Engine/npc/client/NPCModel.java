package rus.iglo.MirseEngine.Engine.npc.client;

import net.minecraft.resources.ResourceLocation;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;
import rus.iglo.MirseEngine.MirseEngine;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NPCModel extends AnimatedGeoModel<NPCEntity> {
    @Override
    public ResourceLocation getModelResource(NPCEntity entity) {
        return new ResourceLocation(MirseEngine.MODID, "geo/" + entity.getGeo());
    }
    @Override
    public ResourceLocation getTextureResource(NPCEntity entity) {
        return new ResourceLocation(MirseEngine.MODID, "textures/entity/" + entity.getTexture());
    }
    @Override
    public ResourceLocation getAnimationResource(NPCEntity entity) {
        return new ResourceLocation(MirseEngine.MODID, "animations/" + entity.getAnimAnim());
    }
}