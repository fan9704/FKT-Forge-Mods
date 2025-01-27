package net.fkt.forgetrain.datagen;

import net.fkt.forgetrain.ForgeTrain;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ForgeTrain.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    public DataGenerators() {}
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Minecraft Data Generator
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider  = event.getLookupProvider();

        // 添加自己的 DataGenerator
        generator.addProvider(event.includeServer(),new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new , LootContextParamSets.BLOCK)),lookupProvider));
        generator.addProvider(event.includeServer(),new ModRecipeProvider(packOutput,lookupProvider));
        // 因為 Block 也會是 Item 所以先把 BlockTagsProvider 實例化出來再註冊到 BlockTags 與 ItemTags
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput,lookupProvider,existingFileHelper);
        generator.addProvider(event.includeServer(),blockTagsProvider);
        generator.addProvider(event.includeServer(),new ModItemTagsProvider(packOutput,lookupProvider,blockTagsProvider.contentsGetter(),existingFileHelper));

        generator.addProvider(event.includeClient(),new ModItemModelProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeClient(),new ModBlockStateProvider(packOutput,existingFileHelper));
    }
}
