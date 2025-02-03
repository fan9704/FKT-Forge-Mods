package net.fkt.forgetrain.entity.custom;

import net.fkt.forgetrain.entity.ModEntities;
import net.fkt.forgetrain.entity.TriceratopsVariant;
import net.fkt.forgetrain.item.ModItems;
import net.fkt.forgetrain.sound.ModSounds;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TriceratopsEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(TriceratopsEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public TriceratopsEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // 決定 Entity
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));

        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        //  用大頭菜引誘
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(ModItems.KOHLRABI.get()), false));

        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }
    // 建造生物素質
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    // 吃 大頭菜
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModItems.KOHLRABI.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TRICERATOPS.get().create(pLevel);
    }

    // 改變生物狀態與重置 Idle 時間
    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    // Variant


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VARIANT, 0);
    }

    private int getTypeVariant(){
        return this.entityData.get(VARIANT);
    }

    public TriceratopsVariant getVariant(){
        return TriceratopsVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(TriceratopsVariant variant){
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT,pCompound.getInt("Variant"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        TriceratopsVariant variant = Util.getRandom(TriceratopsVariant.values(),this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel pLevel, Animal pAnimal, @Nullable AgeableMob pBaby) {
        TriceratopsVariant variant = Util.getRandom(TriceratopsVariant.values(),this.random);
        ((TriceratopsEntity) pBaby).setVariant(variant);
        super.finalizeSpawnChildFromBreeding(pLevel, pAnimal, pBaby);
    }

    // Sounds

    // 生物棲息聲音
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ELDER_GUARDIAN_AMBIENT;
    }
    // 受傷音效
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }
    // 死亡音效
    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SNIFFER_DEATH;
    }
}
