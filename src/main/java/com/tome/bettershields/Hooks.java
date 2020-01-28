package com.tome.bettershields;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DamageSource;

public class Hooks {

	public static float getDamageReduction(LivingEntity victim, DamageSource source, float damage) {
		float f1 = 0.0f;
		if (damage > 0.0F && victim.canBlockDamageSource(source)) {
			victim.damageShield(damage);
			f1 = damage;
			float reduction = 1f;
			Item shield = victim.getActiveItemStack().getItem();
			if (shield instanceof BetterShieldItem) {
				reduction = ((BetterShieldItem) shield).getDamageReduction() / 100f;
			} else if (shield == Items.SHIELD
					|| (!Config.customShieldMaxReduction.get() && victim.getActiveItemStack().isShield(victim))) {
				reduction = 0.75f;
			}
			if (!source.isProjectile() && reduction < 1f)
				f1 = damage * reduction;
			if (!source.isProjectile()) {
				Entity entity = source.getImmediateSource();
				if (entity instanceof LivingEntity) {
					victim.blockUsingShield((LivingEntity) entity);
				}
			}
		}
		return f1;
	}

	public static boolean canThornsApply(ItemStack stack) {
		if (Config.thornsOnShields.get()) {
			return stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ShieldItem;
		} else {
			return stack.getItem() instanceof ArmorItem;
		}
	}

	public static boolean damageEntityFromReturn(DamageSource source, boolean flag) {
		return flag || !source.isProjectile();
	}

}
