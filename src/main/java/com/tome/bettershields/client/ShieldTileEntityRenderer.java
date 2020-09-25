package com.tome.bettershields.client;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import com.tome.bettershields.BetterShields;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldTileEntityRenderer extends ItemStackTileEntityRenderer {

	@Override
	public void func_239207_a_(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack,
			IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		matrixStack.push();
		matrixStack.scale(1, -1, -1);
		boolean flag = stack.getChildTag("BlockEntityTag") != null;
		RenderMaterial rendermaterial = flag ? ModelBakery.LOCATION_SHIELD_BASE
				: ModelBakery.LOCATION_SHIELD_NO_PATTERN;

		Item shield = stack.getItem();
		if (shield == BetterShields.ironShield) {
			rendermaterial = flag ? ShieldTextures.LOCATION_IRON_SHIELD_BASE
					: ShieldTextures.LOCATION_IRON_SHIELD_BASE_NOPATTERN;
		} else if (shield == BetterShields.goldShield) {
			rendermaterial = flag ? ShieldTextures.LOCATION_GOLD_SHIELD_BASE
					: ShieldTextures.LOCATION_GOLD_SHIELD_BASE_NOPATTERN;
		} else if (shield == BetterShields.diamondShield) {
			rendermaterial = flag ? ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE
					: ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN;
		} else if (shield == BetterShields.netheriteShield) {
			rendermaterial = flag ? ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE
					: ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN;
		}

		IVertexBuilder ivertexbuilder = rendermaterial.getSprite().wrapBuffer(ItemRenderer.getEntityGlintVertexBuilder(
				buffer, modelShield.getRenderType(rendermaterial.getAtlasLocation()), true, stack.hasEffect()));
		this.modelShield.func_228294_b_().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F,
				1.0F, 1.0F, 1.0F);
		if (flag) {
			List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(ShieldItem.getColor(stack),
					BannerTileEntity.getPatternData(stack));
			BannerTileEntityRenderer.func_241717_a_(matrixStack, buffer, combinedLight, combinedOverlay,
					this.modelShield.func_228293_a_(), rendermaterial, false, list, stack.hasEffect());
		} else {
			this.modelShield.func_228293_a_().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F,
					1.0F, 1.0F, 1.0F);
		}
		matrixStack.pop();
	}

}
