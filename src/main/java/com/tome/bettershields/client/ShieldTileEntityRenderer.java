package com.tome.bettershields.client;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.tome.bettershields.BetterShields;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ShieldTileEntityRenderer extends BlockEntityWithoutLevelRenderer {

	public static ShieldTileEntityRenderer instance;
	
	public ShieldTileEntityRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
	}
	
	@SubscribeEvent
	public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
		instance = new ShieldTileEntityRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
				Minecraft.getInstance().getEntityModels());
		event.registerReloadListener(instance);
	}

	@Override
	public void renderByItem(ItemStack stack, TransformType p_239207_2_, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		matrixStack.pushPose();
		matrixStack.scale(1, -1, -1);
		boolean flag = stack.getTagElement("BlockEntityTag") != null;
		Material rendermaterial = flag ? ModelBakery.SHIELD_BASE
				: ModelBakery.NO_PATTERN_SHIELD;

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

		VertexConsumer ivertexbuilder = rendermaterial.sprite().wrap(ItemRenderer.getFoilBufferDirect(
				buffer, shieldModel.renderType(rendermaterial.atlasLocation()), true, stack.hasFoil()));
		this.shieldModel.handle().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F,
				1.0F, 1.0F, 1.0F);
		if (flag) {
			List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(stack),
					BannerBlockEntity.getItemPatterns(stack));
			BannerRenderer.renderPatterns(matrixStack, buffer, combinedLight, combinedOverlay,
					this.shieldModel.plate(), rendermaterial, false, list, stack.hasFoil());
		} else {
			this.shieldModel.plate().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F,
					1.0F, 1.0F, 1.0F);
		}
		matrixStack.popPose();
	}

}
