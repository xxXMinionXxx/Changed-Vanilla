package net.ltxprogrammer.changedvanilla.client.render;

import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexSkeletonModel;
import net.ltxprogrammer.changedvanilla.entity.LatexSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexSkeletonRenderer extends AdvancedHumanoidRenderer<LatexSkeleton, LatexSkeletonModel> {
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = ChangedVanilla.modResource("textures/entity/latex_skeleton.png");

    public LatexSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexSkeletonModel(context.bakeLayer(LatexSkeletonModel.LAYER_LOCATION)), ArmorLatexMaleWolfModel.MODEL_SET, 0.5f);
        this.addLayer(new LatexParticlesLayer<>(this, getModel()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(CustomEyesLayer.builder(this, context.getModelSet())
                .withSclera(Color3.fromInt(0x0)).build());
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexSkeleton entity) {
        return DEFAULT_SKIN_LOCATION;
    }
}
