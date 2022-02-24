package com.tome.bettershields;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class Hooks {

	public static boolean canThornsApply(ItemStack stack) {
		if (Config.thornsOnShields.get()) {
			return stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ShieldItem;
		} else {
			return stack.getItem() instanceof ArmorItem;
		}
	}

}
