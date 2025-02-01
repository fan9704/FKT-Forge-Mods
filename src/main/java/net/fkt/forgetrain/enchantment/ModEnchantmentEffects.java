package net.fkt.forgetrain.enchantment;

import com.mojang.serialization.MapCodec;
import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.enchantment.custom.LifeStealEnchantmentEffect;
import net.fkt.forgetrain.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ForgeTrain.MOD_ID);

    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_STRIKER =
            ENTITY_ENCHANTMENT_EFFECTS.register("lightning_striker",()-> LightningStrikerEnchantmentEffect.CODEC);

    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LIFE_STEAL =
            ENTITY_ENCHANTMENT_EFFECTS.register("life_steal",()-> LifeStealEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
