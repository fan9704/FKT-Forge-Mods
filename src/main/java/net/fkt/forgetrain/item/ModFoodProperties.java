package net.fkt.forgetrain.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties KOHLRABI = new FoodProperties.Builder().nutrition(3)
            .saturationModifier(0.25f)
            .effect(new MobEffectInstance(MobEffects.INVISIBILITY, 4000), 100)
            .build();

    public static final FoodProperties HONEY_BERRY = new FoodProperties.Builder().nutrition(2)
            .saturationModifier(0.25f)
            .fast()
            .build();
}
