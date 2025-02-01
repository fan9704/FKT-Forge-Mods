package net.fkt.forgetrain.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;


public record LightningStrikerEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LightningStrikerEnchantmentEffect> CODEC = MapCodec.unit(LightningStrikerEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        for(int i = 0; i < pEnchantmentLevel; ++i) {
            EntityType.LIGHTNING_BOLT.spawn(pLevel,pEntity.getOnPos(), MobSpawnType.TRIGGERED);
        }
        // 附魔等級 10 以上限定技能 四方落雷
        if(pEnchantmentLevel >= 10){
            // 施法給予玩家抗火效果
            if(pItem.owner() != null){
                pItem.owner().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600 ));
            }
            for(int i = 0; i < pEnchantmentLevel-9; i++){
                BlockPos blockPosN = new BlockPos((int) pEntity.getX()+i, (int) pEntity.getY(), (int) pEntity.getZ());
                BlockPos blockPosE = new BlockPos((int) pEntity.getX()-i, (int) pEntity.getY(), (int) pEntity.getZ());
                BlockPos blockPosW = new BlockPos((int) pEntity.getX(), (int) pEntity.getY(), (int) pEntity.getZ()+i);
                BlockPos blockPosS = new BlockPos((int) pEntity.getX(), (int) pEntity.getY(), (int) pEntity.getZ()-i);
                EntityType.LIGHTNING_BOLT.spawn(pLevel,blockPosS, MobSpawnType.TRIGGERED);
                EntityType.LIGHTNING_BOLT.spawn(pLevel,blockPosW, MobSpawnType.TRIGGERED);
                EntityType.LIGHTNING_BOLT.spawn(pLevel,blockPosE, MobSpawnType.TRIGGERED);
                EntityType.LIGHTNING_BOLT.spawn(pLevel,blockPosN, MobSpawnType.TRIGGERED);
            }

        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
