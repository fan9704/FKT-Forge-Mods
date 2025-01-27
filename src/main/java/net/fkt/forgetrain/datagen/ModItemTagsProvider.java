package net.fkt.forgetrain.datagen;

import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.item.ModItems;
import net.fkt.forgetrain.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                           CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput,completableFuture,lookupCompletableFuture, ForgeTrain.MOD_ID,existingFileHelper);

    }
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // 標記下列物品到 TRANSFORMABLE_ITEMS tag 中
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.ALEXANDRITE.get())
                .add(ModItems.RAW_ALEXANDRITE.get())
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.COMPASS);
    }
}
