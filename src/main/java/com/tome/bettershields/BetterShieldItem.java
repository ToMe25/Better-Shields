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
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BetterShieldItem extends ShieldItem {

	private Supplier<Integer> damageReduction;
	private INamedTag<Item> repairMaterial;

	public BetterShieldItem(String registryName, Supplier<Integer> damageReduction, ResourceLocation repairMaterial,
			int durability, boolean fireProof) {
		super((fireProof ? new Properties().isImmuneToFire() : new Properties()).setISTER(() -> getISTER())
				.group(ItemGroup.COMBAT).maxDamage(durability));
		setRegistryName(new ResourceLocation(BetterShields.MODID, registryName));
		this.damageReduction = damageReduction;
		this.repairMaterial = ItemTags.makeWrapperTag(repairMaterial.toString());
		DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
	}

	@OnlyIn(Dist.CLIENT)
	private static Callable<ItemStackTileEntityRenderer> getISTER() {
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
