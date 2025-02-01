package net.fkt.forgetrain.enchantment.custom;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;


public record LifeStealEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LifeStealEnchantmentEffect> CODEC = MapCodec.unit(LifeStealEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        for(int i = 0; i < pEnchantmentLevel; ++i) {
            if(pItem.owner() != null){
                pItem.owner().heal(1);
            }
        }
        // 附魔等級 10 以上限定技能 極限回復
        if(pEnchantmentLevel >= 10){
            // 施法給予玩家增加生命效果
            if(pItem.owner() != null){
                pItem.owner().addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600 ));
            }

        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
