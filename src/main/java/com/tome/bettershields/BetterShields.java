package com.tome.bettershields;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
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
	public static BetterShieldItem netheriteShield;

	public BetterShields() {
		new Config();
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		final IForgeRegistry<Item> registry = e.getRegistry();
		ironShield = new BetterShieldItem("iron_shield", Config.ironDamageReduction,
				"forge:ingots/iron", Config.ironDurability.get(), false);
		goldShield = new BetterShieldItem("gold_shield", Config.goldDamageReduction,
				"forge:ingots/gold", Config.goldDurability.get(), false);
		diamondShield = new BetterShieldItem("diamond_shield", Config.diamondDamageReduction,
				"forge:gems/diamond", Config.diamondDurability.get(), false);
		netheriteShield = new BetterShieldItem("netherite_shield", Config.netheriteDamageReduction,
				"forge:ingots/netherite", Config.netheriteDurability.get(), true);
		registry.registerAll(ironShield, goldShield, diamondShield, netheriteShield);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> e) {
		final IForgeRegistry<RecipeSerializer<?>> registry = e.getRegistry();
		registry.register(ShieldRecipes.SERIALIZER.setRegistryName(new ResourceLocation(MODID, "shield_decoration")));
	}

	/**
	 * Creates a {@link TranslationTextComponent} for the when blocking tooltip.
	 * 
	 * @return the new text component.
	 */
	public static Component getBlockingTextComponent() {
		return new TranslatableComponent("bettershields.shield_blocking").withStyle(ChatFormatting.GRAY);
	}

	/**
	 * Creates a {@link TranslationTextComponent} for the damage reduction tooltip.
	 * 
	 * @param reduction the damage reduction of the shield for which the text
	 *                  component will be used.
	 * @return the new text component.
	 */
	public static Component getDamageReductionTextComponent(int reduction) {
		return new TranslatableComponent("bettershields.shield_damage_reduction", reduction)
				.withStyle(ChatFormatting.DARK_GREEN);
	}

}
