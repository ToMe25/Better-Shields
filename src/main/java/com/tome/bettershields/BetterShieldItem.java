package com.tome.bettershields;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;

public class BetterShieldItem extends ShieldItem {

	public BetterShieldItem(ItemGroup group, String regsitryName) {
		super(new Properties().group(group));
		setRegistryName(new ResourceLocation(BetterShields.MODID, regsitryName));
	}

}