package net.fkt.forgetrain.util;

import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.component.ModDataComponentTypes;
import net.fkt.forgetrain.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties(){
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(ForgeTrain.MOD_ID,"used"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.get(ModDataComponentTypes.COORDINATED.get())!= null? 1f : 0f);
    }
}
