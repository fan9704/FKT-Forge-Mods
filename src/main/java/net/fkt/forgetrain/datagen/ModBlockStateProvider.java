package net.fkt.forgetrain.datagen;

import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper){
        super(output, ForgeTrain.MOD_ID,existingFileHelper);
    }
    @Override
    protected void registerStatesAndModels() {
        // 註冊所有 Block 到 BlockState and Model
        blockWithItem(ModBlocks.ALEXANDRITE_BLOCK);
        blockWithItem(ModBlocks.RAW_ALEXANDRITE_BLOCK);

        blockWithItem(ModBlocks.ALEXANDRITE_ORE);
        blockWithItem(ModBlocks.ALEXANDRITE_DEEPSLATE_ORE);

        blockWithItem(ModBlocks.MAGIC_BLOCK);
    }
    // 簡易的 Helper 方法 導入 Block 自動註冊
    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        // cubeAll 自動調用 ModelFile 後傳入註冊表
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}
