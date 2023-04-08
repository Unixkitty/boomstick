package com.unixkitty.boomstick;

import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.DoubleValue targetExplosionResistance;

    static
    {
        ForgeConfigSpec.Builder commonConfig = new ForgeConfigSpec.Builder();

        {
            commonConfig.push("General");
            targetExplosionResistance = commonConfig.defineInRange("targetExplosionResistance", Blocks.OBSIDIAN.getExplosionResistance() / 2, 0, Blocks.BEDROCK.getExplosionResistance());
            commonConfig.pop();
        }
//        commonConfig.comment("Miscellaneous settings").push(CATEGORY_MISC);

        COMMON_CONFIG = commonConfig.build();
    }
}
