package net.fkt.forgetrain.worldgen.tree;

import net.fkt.forgetrain.ForgeTrain;
import net.fkt.forgetrain.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower WALNUT = new TreeGrower(ForgeTrain.MOD_ID + ":walnut",
            Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_KEY), Optional.empty());
}
