package net.fkt.forgetrain;

import com.mojang.logging.LogUtils;
import net.fkt.forgetrain.block.ModBlocks;
import net.fkt.forgetrain.component.ModDataComponentTypes;
import net.fkt.forgetrain.item.ModCreativeModeTabs;
import net.fkt.forgetrain.item.ModItems;
import net.fkt.forgetrain.sound.ModSounds;
import net.fkt.forgetrain.util.ModItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeTrain.MOD_ID)
public class ForgeTrain
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fkttestrainmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeTrain()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        ModSounds.register(modEventBus);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        // 添加 ModItem ALEXANDRITE/RAW_ALEXANDRITE 至 "Ingredient" 選項卡
//        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
//            event.accept(ModItems.ALEXANDRITE);
//            event.accept(ModItems.RAW_ALEXANDRITE);
//        }
//        // 添加 ModItem ALEXANDRITE_BLOCK/RAW_ALEXANDRITE_BLOCK 至 "Building Blocks" 選項卡
//        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
//            event.accept(ModBlocks.ALEXANDRITE_BLOCK);
//            event.accept(ModBlocks.RAW_ALEXANDRITE_BLOCK);
//        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // 添加自製的 ItemProperties
            ModItemProperties.addCustomItemProperties();
        }
    }
}
