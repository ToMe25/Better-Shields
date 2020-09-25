package com.tome.bettershields.datagen;

import java.util.function.Consumer;

import com.tome.bettershields.BetterShields;
import com.tome.bettershields.ShieldRecipes;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
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
				.addCriterion("has_item", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BetterShields.goldShield).key(SHIELD_CHAR, Items.SHIELD)
				.key(MATERIAL_CHAR, Tags.Items.INGOTS_GOLD)
				.patternLine("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).patternLine(" " + MATERIAL_CHAR + " ")
				.addCriterion("has_item", hasItem(Tags.Items.INGOTS_GOLD)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(BetterShields.diamondShield).key(SHIELD_CHAR, Items.SHIELD)
				.key(MATERIAL_CHAR, Tags.Items.GEMS_DIAMOND)
				.patternLine("" + MATERIAL_CHAR + SHIELD_CHAR + MATERIAL_CHAR).patternLine(" " + MATERIAL_CHAR + " ")
				.addCriterion("has_item", hasItem(Tags.Items.GEMS_DIAMOND)).build(consumer);
		specialRecipe(consumer, ShieldRecipes.SERIALIZER);
	}

	private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {
		CustomRecipeBuilder.func_218656_a(serializer).build(consumer,
				BetterShields.MODID + ":dynamic/" + serializer.getRegistryName().getPath());
	}

}
