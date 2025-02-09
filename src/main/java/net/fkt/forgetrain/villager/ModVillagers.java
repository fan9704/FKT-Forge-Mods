package net.fkt.forgetrain.villager;

import com.google.common.collect.ImmutableSet;
import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.block.ModBlocks;
import net.fkt.forgetrain.sound.ModSounds;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    // 興趣物件
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, ForgeTrain.MOD_ID);
    // 職業
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ForgeTrain.MOD_ID);
    // getPossibleStates 也就是代表也可以限制方塊狀態 才能提供職業
    public static final RegistryObject<PoiType> KAUPEN_POI = POI_TYPES.register("kaupen_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.CHAIR.get().getStateDefinition().getPossibleStates()),1,1));

    public static final RegistryObject<VillagerProfession> KAUPENGER = VILLAGER_PROFESSIONS.register("kaupenger",
            () -> new VillagerProfession("kaupenger", holder -> holder.value() == KAUPEN_POI.get(),
                    holder -> holder.value() == KAUPEN_POI.get(), ImmutableSet.of(),ImmutableSet.of(),
                    ModSounds.MAGIC_BLOCK_HIT.get()));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
