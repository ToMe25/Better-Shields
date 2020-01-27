package com.tome.bettershields.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.tome.bettershields.BetterShields;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldTileEntityRenderer extends ItemStackTileEntityRenderer {

	private final BannerTileEntity banner = new BannerTileEntity();
	private final ShieldModel modelShield = new ShieldModel();

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		if (itemStackIn.getItem() == BetterShields.ironShield) {
			if (itemStackIn.getChildTag("BlockEntityTag") != null) {
				this.banner.loadFromItemStack(itemStackIn, ShieldItem.getColor(itemStackIn));
				Minecraft.getInstance().getTextureManager()
						.bindTexture(ShieldTextures.IRON_SHIELD_DESIGNS.getResourceLocation(
								this.banner.getPatternResourceLocation(), this.banner.getPatternList(),
								this.banner.getColorList()));
			} else {
				Minecraft.getInstance().getTextureManager().bindTexture(ShieldTextures.IRON_SHIELD_BASE_TEXTURE);
			}

			GlStateManager.pushMatrix();
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);
			this.modelShield.render();
			if (itemStackIn.hasEffect()) {
				this.renderEffect(this.modelShield::render);
			}
			GlStateManager.popMatrix();
		} else if (itemStackIn.getItem() == BetterShields.goldShield) {
			if (itemStackIn.getChildTag("BlockEntityTag") != null) {
				this.banner.loadFromItemStack(itemStackIn, ShieldItem.getColor(itemStackIn));
				Minecraft.getInstance().getTextureManager()
						.bindTexture(ShieldTextures.GOLD_SHIELD_DESIGNS.getResourceLocation(
								this.banner.getPatternResourceLocation(), this.banner.getPatternList(),
								this.banner.getColorList()));
			} else {
				Minecraft.getInstance().getTextureManager().bindTexture(ShieldTextures.GOLD_SHIELD_BASE_TEXTURE);
			}

			GlStateManager.pushMatrix();
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);
			this.modelShield.render();
			if (itemStackIn.hasEffect()) {
				this.renderEffect(this.modelShield::render);
			}
			GlStateManager.popMatrix();
		} else if (itemStackIn.getItem() == BetterShields.diamondShield) {
			if (itemStackIn.getChildTag("BlockEntityTag") != null) {
				this.banner.loadFromItemStack(itemStackIn, ShieldItem.getColor(itemStackIn));
				Minecraft.getInstance().getTextureManager()
						.bindTexture(ShieldTextures.DIAMOND_SHIELD_DESIGNS.getResourceLocation(
								this.banner.getPatternResourceLocation(), this.banner.getPatternList(),
								this.banner.getColorList()));
			} else {
				Minecraft.getInstance().getTextureManager().bindTexture(ShieldTextures.DIAMOND_SHIELD_BASE_TEXTURE);
			}

			GlStateManager.pushMatrix();
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);
			this.modelShield.render();
			if (itemStackIn.hasEffect()) {
				this.renderEffect(this.modelShield::render);
			}
			GlStateManager.popMatrix();
		} else {
			super.renderByItem(itemStackIn);
		}
	}

	private void renderEffect(Runnable renderModelFunction) {
		GlStateManager.color3f(0.5019608F, 0.2509804F, 0.8F);
		Minecraft.getInstance().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
		ItemRenderer.renderEffect(Minecraft.getInstance().getTextureManager(), renderModelFunction, 1);
	}

}
