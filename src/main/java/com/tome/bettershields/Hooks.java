package com.tome.bettershields;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class Hooks {

	public static float getDamageReduction(LivingEntity victim, DamageSource source, float damage) {
		float f1 = 0.0f;
		if (damage > 0.0F && victim.canBlockDamageSource(source)) {
			victim.damageShield(damage);
			f1 = damage;
			float reduction = 0.75f;
			if(!source.isProjectile() && reduction < 1)
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

}
