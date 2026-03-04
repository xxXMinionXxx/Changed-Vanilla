package net.ltxprogrammer.changedvanilla.init;

import com.mojang.datafixers.util.Pair;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedVanillaEntities {
    private static final List<Pair<Supplier<EntityType<? extends ChangedEntity>>, Supplier<AttributeSupplier.Builder>>> ATTR_FUNC_REGISTRY = new ArrayList<>();
    private static final List<ChangedEntities.VoidConsumer> INIT_FUNC_REGISTRY = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChangedVanilla.MODID);
    public static final Map<RegistryObject<? extends EntityType<?>>, RegistryObject<ForgeSpawnEggItem>> SPAWN_EGGS = new HashMap<>();

    public static final RegistryObject<EntityType<LatexSkeleton>> LATEX_SKELETON = registerWithEgg("latex_skeleton", 0x26252a, 0xd7d7d7,
            EntityType.Builder.of(LatexSkeleton::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F),
            SpawnPlacements.Type.ON_GROUND, LatexSkeleton::checkEntitySpawnRules);

    private static <T extends ChangedEntity> RegistryObject<EntityType<T>> registerWithEgg(
            String name,
            int eggBack,
            int eggHighlight,
            EntityType.Builder<T> builder,
            SpawnPlacements.Type spawnType,
            SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        return registerWithEgg(name, eggBack, eggHighlight, builder, spawnType, spawnPredicate, T::createLatexAttributes);
    }

    private static <T extends ChangedEntity> RegistryObject<EntityType<T>> registerWithEgg(
            String name,
            int eggBack,
            int eggHighlight,
            EntityType.Builder<T> builder,
            SpawnPlacements.Type spawnType,
            SpawnPlacements.SpawnPredicate<T> spawnPredicate,
            Supplier<AttributeSupplier.Builder> attributes) {
        String regName = ChangedVanilla.modResourceStr(name);
        RegistryObject<EntityType<T>> entityType = REGISTRY.register(name, () -> builder.build(regName));
        INIT_FUNC_REGISTRY.add(ChangedEntity.getInit(entityType, spawnType, spawnPredicate));
        ATTR_FUNC_REGISTRY.add(new Pair<>(entityType::get, attributes));
        RegistryObject<ForgeSpawnEggItem> spawnEggItem = ChangedVanillaItems.REGISTRY.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggBack, eggHighlight, new Item.Properties()));
        SPAWN_EGGS.put(entityType, spawnEggItem);
        return entityType;
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> INIT_FUNC_REGISTRY.forEach(ChangedEntities.VoidConsumer::accept));
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ATTR_FUNC_REGISTRY.forEach((pair) -> event.put(pair.getFirst().get(), pair.getSecond().get().build()));
    }
}
