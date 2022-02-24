package com.tome.bettershields.datagen;

import java.util.function.Consumer;

import com.tome.bettershields.BetterShields;
import com.tome.bettershields.ShieldRecipes;

import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.common.Tags;

public class BetterShieldsRecipeProvider extends RecipeProvider {

	private static final char SHIELD_CHAR = 'C';
	private static final char MATERIAL_CHAR = 'M';

	public BetterShieldsRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(BetterShields.ironShield).define(SHIELD_CHAR, Items.SHIELD)
				.define(MATERIAL_CHAR, Tags.Items.INGOTS_IRON)
				.pattern("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).pattern(" " + MATERIAL_CHAR + " ")
				.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).save(consumer);
		ShapedRecipeBuilder.shaped(BetterShields.goldShield).define(SHIELD_CHAR, Items.SHIELD)
				.define(MATERIAL_CHAR, Tags.Items.INGOTS_GOLD)
				.pattern("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).pattern(" " + MATERIAL_CHAR + " ")
				.unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD)).save(consumer);
		ShapedRecipeBuilder.shaped(BetterShields.diamondShield).define(SHIELD_CHAR, Items.SHIELD)
				.define(MATERIAL_CHAR, Tags.Items.GEMS_DIAMOND)
				.pattern("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).pattern(" " + MATERIAL_CHAR + " ")
				.unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(consumer);
		smithingReinforce(consumer, BetterShields.diamondShield, BetterShields.netheriteShield);
		specialRecipe(consumer, ShieldRecipes.SERIALIZER);
	}

	private void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleRecipeSerializer<?> serializer) {
		SpecialRecipeBuilder.special(serializer).save(consumer,
				BetterShields.MODID + ":dynamic/" + serializer.getRegistryName().getPath());
	}

	private static void smithingReinforce(Consumer<FinishedRecipe> recipeConsumer, Item itemToReinforce, Item output) {
		UpgradeRecipeBuilder
				.smithing(Ingredient.of(itemToReinforce), Ingredient.of(Items.NETHERITE_INGOT),
						output)
				.unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeConsumer,
						BetterShields.MODID + ":smithing/" + output.asItem().getRegistryName().getPath());
	}

}
