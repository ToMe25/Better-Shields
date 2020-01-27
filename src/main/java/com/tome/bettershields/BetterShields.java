package com.tome.bettershields;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(BetterShields.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class BetterShields {

	public static final String MODID = "bettershields";

	public static BetterShieldItem ironShield;
	public static BetterShieldItem goldShield;
	public static BetterShieldItem diamondShield;

	public BetterShields() {
		new Config();
		ironShield = new BetterShieldItem("iron_shield", () -> Config.ironDamageReduction.get(),
				new ResourceLocation("forge", "ingots/iron"), Config.ironDurability.get());
		goldShield = new BetterShieldItem("gold_shield", () -> Config.goldDamageReduction.get(),
				new ResourceLocation("forge", "ingots/gold"), Config.goldDurability.get());
		diamondShield = new BetterShieldItem("diamond_shield", () -> Config.diamondDamageReduction.get(),
				new ResourceLocation("forge", "gems/diamond"), Config.diamondDurability.get());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		final IForgeRegistry<Item> registry = e.getRegistry();
		registry.registerAll(ironShield, goldShield, diamondShield);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> e) {
		final IForgeRegistry<IRecipeSerializer<?>> registry = e.getRegistry();
		registry.register(ShieldRecipes.SERIALIZER.setRegistryName(new ResourceLocation(MODID, "shield_decoration")));
	}

	public static ITextComponent getDamageReductionTextComponent(int reduction) {
		return new TranslationTextComponent("bettershields.banner_damage_reduction", reduction)
				.applyTextStyle(TextFormatting.DARK_GREEN);
	}

}
