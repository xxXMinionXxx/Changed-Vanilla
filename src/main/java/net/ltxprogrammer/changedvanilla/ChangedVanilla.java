package net.ltxprogrammer.changedvanilla;

import com.mojang.logging.LogUtils;
import net.ltxprogrammer.changedvanilla.init.ChangedVanillaEntities;
import net.ltxprogrammer.changedvanilla.init.ChangedVanillaTransfurVariants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChangedVanilla.MODID)
public class ChangedVanilla {
    public static final String MODID = "changedvanilla";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChangedVanilla(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();

        ChangedVanillaEntities.REGISTRY.register(modEventBus);
        ChangedVanillaTransfurVariants.REGISTRY.register(modEventBus);
    }

    public static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static String modResourceStr(String path) {
        return "changedvanilla:" + path;
    }
}
