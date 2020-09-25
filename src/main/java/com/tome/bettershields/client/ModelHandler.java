package com.tome.bettershields.client;

import com.tome.bettershields.BetterShields;

import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModelHandler {

	@SubscribeEvent
	public static void init(FMLClientSetupEvent event) {
		addShieldPropertyOverrides(new ResourceLocation(BetterShields.MODID, "blocking"),
				(stack, world, entity) -> entity != null && entity.isHandActive()
						&& entity.getActiveItemStack() == stack ? 1.0F : 0.0F,
				BetterShields.ironShield, BetterShields.goldShield, BetterShields.diamondShield,
				BetterShields.netheriteShield);
	}

	private static void addShieldPropertyOverrides(ResourceLocation override, IItemPropertyGetter propertyGetter,
			IItemProvider... shields) {
		for (IItemProvider shield : shields) {
			ItemModelsProperties.registerProperty(shield.asItem(), override, propertyGetter);
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onStitch(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
			for (RenderMaterial textures : new RenderMaterial[] { ShieldTextures.LOCATION_IRON_SHIELD_BASE,
					ShieldTextures.LOCATION_IRON_SHIELD_BASE_NOPATTERN, ShieldTextures.LOCATION_GOLD_SHIELD_BASE,
					ShieldTextures.LOCATION_GOLD_SHIELD_BASE_NOPATTERN, ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE,
					ShieldTextures.LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN,
					ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE,
					ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN }) {
				event.addSprite(textures.getTextureLocation());
			}
		}
	}

}