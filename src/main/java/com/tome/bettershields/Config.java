package com.tome.bettershields;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config {

	private static CommentedFileConfig cfg;
	private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	private static final String CATEGORY_GENERAL = "general";

	public static BooleanValue customShieldMaxReduction;
	public static ConfigValue<Integer> defaultDamageReduction;
	public static ConfigValue<Integer> ironDamageReduction;
	public static ConfigValue<Integer> goldDamageReduction;
	public static ConfigValue<Integer> diamondDamageReduction;
	public static ConfigValue<Integer> netheriteDamageReduction;
	public static ConfigValue<Integer> ironDurability;
	public static ConfigValue<Integer> goldDurability;
	public static ConfigValue<Integer> diamondDurability;
	public static ConfigValue<Integer> netheriteDurability;
	public static BooleanValue thornsOnShields;
	public static BooleanValue enableDamageReduction;

	public Config() {
		cfg = CommentedFileConfig
				.builder(new File(FMLPaths.CONFIGDIR.get().toString(), BetterShields.MODID + "-common.toml")).sync()
				.autosave().build();
		cfg.load();
		initConfig();
		ForgeConfigSpec spec = builder.build();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, cfg.getFile().getName());
		spec.setConfig(cfg);
	}

	private void initConfig() {
		builder.comment("The general configuration for this mod").push(CATEGORY_GENERAL).pop();
		customShieldMaxReduction = getBoolean("customShieldMaxReduction", CATEGORY_GENERAL, true,
				"If set to true Shields added by other mods block 100% of the incomming damage, "
						+ "if set to false the will block the amount set in defaultDamageReduction.");
		defaultDamageReduction = getInt("defaultDamageReduction", CATEGORY_GENERAL, 75,
				"The portion of the incomming damage Vanilla Shields block in percent.");
		ironDamageReduction = getInt("ironDamageReduction", CATEGORY_GENERAL, 90,
				"The portion of the incomming damage Iron Shields block in percent.");
		goldDamageReduction = getInt("goldDamageReduction", CATEGORY_GENERAL, 85,
				"The portion of the incomming damage Gold Shields block in percent.");
		diamondDamageReduction = getInt("diamondDamageReduction", CATEGORY_GENERAL, 98,
				"The portion of the incomming damage Diamond Shields block in percent.");
		netheriteDamageReduction = getInt("netheriteDamageReduction", CATEGORY_GENERAL, 100,
				"The portion of the incomming damage Netherite Shields block in percent.");
		ironDurability = getInt("ironDurability", CATEGORY_GENERAL, 920, "The durability of the Iron Shield.");
		goldDurability = getInt("goldDurability", CATEGORY_GENERAL, 130, "The durability of the Gold Shield.");
		diamondDurability = getInt("diamondDurability", CATEGORY_GENERAL, 4600,
				"The durability of the Diamond Shield.");
		netheriteDurability = getInt("netheriteDurability", CATEGORY_GENERAL, 6300,
				"The durability of the Netherite Shield.");
		thornsOnShields = getBoolean("thornsOnShields", CATEGORY_GENERAL, true,
				"Allows the Thorns enchantment to by applied to Shields");
		enableDamageReduction = getBoolean("enableDamageReduction", CATEGORY_GENERAL, true,
				"Enables modified damage reduction values for shields.");
	}

	private static BooleanValue getBoolean(String name, String category, boolean defaultValue, String comment) {
		String path = category + "." + name;
		return builder.comment(comment, "Default: " + defaultValue).define(path, defaultValue);
	}

	private static ConfigValue<Integer> getInt(String name, String category, int defaultValue, String comment) {
		String path = category + "." + name;
		return builder.comment(comment, "Default: " + defaultValue).define(path, defaultValue);
	}

}
