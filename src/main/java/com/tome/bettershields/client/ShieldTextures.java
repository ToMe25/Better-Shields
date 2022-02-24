package com.tome.bettershields.client;

import com.tome.bettershields.BetterShields;

import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldTextures {
	public static final Material LOCATION_IRON_SHIELD_BASE = material("entity/iron_shield_base");
	public static final Material LOCATION_IRON_SHIELD_BASE_NOPATTERN = material("entity/iron_shield_base_nopattern");

	public static final Material LOCATION_GOLD_SHIELD_BASE = material("entity/gold_shield_base");
	public static final Material LOCATION_GOLD_SHIELD_BASE_NOPATTERN = material("entity/gold_shield_base_nopattern");

	public static final Material LOCATION_DIAMOND_SHIELD_BASE = material("entity/diamond_shield_base");
	public static final Material LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN = material("entity/diamond_shield_base_nopattern");

	public static final Material LOCATION_NETHERITE_SHIELD_BASE = material("entity/netherite_shield_base");
	public static final Material LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN = material("entity/netherite_shield_base_nopattern");

	@SuppressWarnings("deprecation")
	private static Material material(String path) {
		return new Material(
				TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(BetterShields.MODID, path));
    }
}
