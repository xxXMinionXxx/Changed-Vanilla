package net.ltxprogrammer.changedvanilla.entity;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.ComplexRenderer;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LatexFoxPartial extends LatexFox implements ComplexRenderer {
    public LatexFoxPartial(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.NONE;
    }

    @Override
    protected boolean targetSelectorTest(LivingEntity livingEntity) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSkinTextureLocation() {
        if (getUnderlyingPlayer() instanceof AbstractClientPlayer clientPlayer)
            return clientPlayer.getSkinTextureLocation();
        return DefaultPlayerSkin.getDefaultSkin(this.getUUID());
    }

    @OnlyIn(Dist.CLIENT)
    public String getModelName() {
        if (getUnderlyingPlayer() instanceof AbstractClientPlayer clientPlayer)
            return clientPlayer.getModelName();
        return DefaultPlayerSkin.getSkinModelName(this.getUUID());
    }
}
