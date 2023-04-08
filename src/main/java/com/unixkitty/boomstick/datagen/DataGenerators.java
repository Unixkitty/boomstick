package com.unixkitty.boomstick.datagen;

import com.unixkitty.boomstick.Boomstick;
import com.unixkitty.boomstick.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Boomstick.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        event.getGenerator().addProvider(event.includeServer(), new CraftingTableRecipes(event.getGenerator()));
    }

    private static class CraftingTableRecipes extends RecipeProvider
    {
        public CraftingTableRecipes(DataGenerator generator)
        {
            super(generator);
        }

        @Override
        protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer)
        {
            ShapedRecipeBuilder.shaped(ModRegistry.BOOMSTICK.get())
                    .define('s', Items.STICK)
                    .define('C', Items.COBBLESTONE)
                    .define('T', Items.TNT)
                    .pattern("s")
                    .pattern("C")
                    .pattern("T")
                    .unlockedBy("has_item", has(Items.TNT))
                    .save(consumer, "boomstick:boomstick_full");

            ShapelessRecipeBuilder.shapeless(ModRegistry.BOOMSTICK.get())
                    .requires(Items.LEVER)
                    .requires(Items.TNT)
                    .unlockedBy("has_item", has(Items.TNT))
                    .save(consumer);
        }

        public String getName()
        {
            return Boomstick.MODID + " " + this.getClass().getSimpleName();
        }
    }
}
