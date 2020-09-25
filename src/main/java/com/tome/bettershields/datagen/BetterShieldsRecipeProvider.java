package com.tome.bettershields.datagen;

import java.util.function.Consumer;

import com.tome.bettershields.BetterShields;
import com.tome.bettershields.ShieldRecipes;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.common.Tags;

public class BetterShieldsRecipeProvider extends RecipeProvider {

	private static final char SHIELD_CHAR = 'C';
	private static final char MATERIAL_CHAR = 'M';

	public BetterShieldsRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(BetterShields.ironShield).key(SHIELD_CHAR, Items.SHIELD)
				.key(MATERIAL_CHAR, Tags.Items.INGOTS_IRON)
				.patternLine("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).patternLine(" " + MATERIAL_CHAR + " ")
				.addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BetterShields.goldShield).key(SHIELD_CHAR, Items.SHIELD)
				.key(MATERIAL_CHAR, Tags.Items.INGOTS_GOLD)
				.patternLine("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).patternLine(" " + MATERIAL_CHAR + " ")
				.addCriterion("has_gold_ingot", hasItem(Tags.Items.INGOTS_GOLD)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BetterShields.diamondShield).key(SHIELD_CHAR, Items.SHIELD)
				.key(MATERIAL_CHAR, Tags.Items.GEMS_DIAMOND)
				.patternLine("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).patternLine(" " + MATERIAL_CHAR + " ")
				.addCriterion("has_diamond", hasItem(Tags.Items.GEMS_DIAMOND)).build(consumer);
		smithingReinforce(consumer, BetterShields.diamondShield, BetterShields.netheriteShield);
		specialRecipe(consumer, ShieldRecipes.SERIALIZER);
	}

	private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {
		CustomRecipeBuilder.customRecipe(serializer).build(consumer,
				BetterShields.MODID + ":dynamic/" + serializer.getRegistryName().getPath());
	}

	private static void smithingReinforce(Consumer<IFinishedRecipe> recipeConsumer, Item itemToReinforce, Item output) {
		SmithingRecipeBuilder
				.smithingRecipe(Ingredient.fromItems(itemToReinforce), Ingredient.fromItems(Items.NETHERITE_INGOT),
						output)
				.addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT))
				.build(recipeConsumer, ":smithing/" + output.asItem().getRegistryName().getPath());
	}

}
