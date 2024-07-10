package rus.iglo.MirseEngine.Engine.npc.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import rus.iglo.MirseEngine.Engine.npc.custom.NPCEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NPCRenderer extends GeoEntityRenderer<NPCEntity> {
    public NPCRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NPCModel());
        // провер
        this.shadowRadius = 0.0f;
    }
    @Override
    public RenderType getRenderType(NPCEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        // про
        stack.scale(1.0f, 1.0f, 1.0f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
