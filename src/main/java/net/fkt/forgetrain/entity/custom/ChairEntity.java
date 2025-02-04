package net.fkt.forgetrain.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> pEntityType, Level world) {
        super(pEntityType, world);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
    // 離開座位 刪除 Entity
    @Override
    protected void removePassenger(Entity pPassenger) {
        super.removePassenger(pPassenger);
        this.kill();
    }
}
