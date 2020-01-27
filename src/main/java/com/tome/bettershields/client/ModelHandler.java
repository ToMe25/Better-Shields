package com.tome.bettershields.client;

import java.util.Map;

import com.tome.bettershields.BetterShields;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModelHandler {

	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent e) {
		final Map<ResourceLocation, IBakedModel> registry = e.getModelRegistry();
		replaceShieldModel(registry, "iron");
		replaceShieldModel(registry, "gold");
		replaceShieldModel(registry, "diamond");
	}

	private static void replaceShieldModel(Map<ResourceLocation, IBakedModel> registry, String material) {
		ModelResourceLocation location = new ModelResourceLocation(BetterShields.MODID + ":" + material + "_shield",
				"inventory");
		IBakedModel originalModel = registry.get(location);
		registry.put((location), new ShieldModel(originalModel));
	}

}