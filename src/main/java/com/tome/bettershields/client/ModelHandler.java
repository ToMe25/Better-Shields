package com.tome.bettershields.client;

import com.tome.bettershields.BetterShields;

import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModelHandler {

	@SubscribeEvent
	public static void init(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			addShieldPropertyOverrides(new ResourceLocation(BetterShields.MODID, "blocking"), (stack, world, entity,
					seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
					BetterShields.ironShield, BetterShields.goldShield, BetterShields.diamondShield,
					BetterShields.netheriteShield);
		});
	}

	private static void addShieldPropertyOverrides(ResourceLocation override, ClampedItemPropertyFunction propertyGetter,
			ItemLike... shields) {
		for (ItemLike shield : shields) {
			ItemProperties.register(shield.asItem(), override, propertyGetter);
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onStitch(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			for (Material textures : new Material[] { ShieldTextures.LOCATION_IRON_SHIELD_BASE,
					ShieldTextures.LOCATION_IRON_SHIELD_BASE_NOPATTERN, ShieldTextures.LOCATION_GOLD_SHIELD_BASE,
					ShieldTextures.LOCATION_GOLD_SHIELD_BASE_NOPATTERN, ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE,
					ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN,
					ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE,
					ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN }) {
				event.addSprite(textures.texture());
			}
		}
	}

}