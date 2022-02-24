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
import net.minecraft.tags.ITag;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BetterShieldItem extends ShieldItem {

	private Supplier<Integer> damageReduction;
	private LazyValue<Ingredient> repairMaterial;

	public BetterShieldItem(String registryName, Supplier<Integer> damageReduction, ITag<Item> repairMaterial,
			int durability, boolean fireProof) {
		this(new ResourceLocation(BetterShields.MODID, registryName), damageReduction,
				() -> Ingredient.fromTag(repairMaterial), durability, fireProof);
	}

	public BetterShieldItem(ResourceLocation registryName, Supplier<Integer> damageReduction,
			Supplier<Ingredient> repairMaterial, int durability, boolean fireProof) {
		super((fireProof ? new Properties().isImmuneToFire() : new Properties()).setISTER(() -> getISTER())
				.group(ItemGroup.COMBAT).maxDamage(durability));
		setRegistryName(registryName);
		this.damageReduction = damageReduction;
		this.repairMaterial = new LazyValue<>(repairMaterial);
		DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
	}

	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> getISTER() {
		return ShieldTileEntityRenderer::new;
	}

	/**
	 * Gets the percentage of the damage received this shield blocks.
	 * 
	 * @return The percentage of the damage received this shield blocks.
	 */
	public int getDamageReduction() {
		return damageReduction.get();
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repairMaterial.getValue().test(repair);
	}

	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

}
