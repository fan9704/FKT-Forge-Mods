package net.fkt.forgetrain.item.custom;

import net.fkt.forgetrain.block.ModBlocks;
import net.fkt.forgetrain.component.ModDataComponentTypes;
import net.fkt.forgetrain.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block,Block> CHISEL_MAPS = Map.of(
            Blocks.STONE,Blocks.STONE_BRICKS,
            Blocks.END_STONE,Blocks.END_STONE_BRICKS,
            Blocks.DEEPSLATE,Blocks.DEEPSLATE_BRICKS,
            Blocks.IRON_BLOCK,Blocks.DIAMOND_BLOCK,
            Blocks.DIRT, ModBlocks.ALEXANDRITE_BLOCK.get()
    );
    public ChiselItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(CHISEL_MAPS.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(pContext.getClickedPos(),CHISEL_MAPS.get(clickedBlock).defaultBlockState());
                pContext.getItemInHand().hurtAndBreak(1,((ServerLevel) level) ,((ServerPlayer) pContext.getPlayer()),
                        item -> pContext.getPlayer().onEquippedItemBroken(item,EquipmentSlot.MAINHAND));
                level.playSound(null,pContext.getClickedPos(), ModSounds.CHISEL_USE.get(), SoundSource.BLOCKS);

                pContext.getItemInHand().set(ModDataComponentTypes.COORDINATED.get(),pContext.getClickedPos());
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag){
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.translatable("tooltip.fkttestrainmod.chisel.shift_down"));
        }else{
            pTooltipComponents.add(Component.translatable("tooltip.fkttestrainmod.chisel"));
        }

        if(pStack.get(ModDataComponentTypes.COORDINATED.get()) != null){
            pTooltipComponents.add(Component.literal("Last Block changed at " + pStack.get(ModDataComponentTypes.COORDINATED.get())));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
