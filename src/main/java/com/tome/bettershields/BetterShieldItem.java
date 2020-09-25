package com.tome.bettershields;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import com.tome.bettershields.client.ShieldTileEntityRenderer;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BetterShieldItem extends ShieldItem {

	private Supplier<Integer> damageReduction;
	private Tag<Item> repairMaterial;

	public BetterShieldItem(String registryName, Supplier<Integer> damageReduction, ResourceLocation repairMaterial,
			int durability) {
		super(new Properties().setTEISR(() -> getTEISR()).group(ItemGroup.COMBAT).maxDamage(durability));
		setRegistryName(new ResourceLocation(BetterShields.MODID, registryName));
		this.damageReduction = damageReduction;
		this.repairMaterial = new ItemTags.Wrapper(repairMaterial);
		DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
	}

	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> getTEISR() {
		return ShieldTileEntityRenderer::new;
	}

	public int getDamageReduction() {
		return damageReduction.get();
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repairMaterial.contains(repair.getItem());
	}

	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

}
