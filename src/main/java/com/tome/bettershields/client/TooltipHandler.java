package com.tome.bettershields.client;

import com.tome.bettershields.BetterShieldItem;
import com.tome.bettershields.BetterShields;
import com.tome.bettershields.Config;

import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID)
public class TooltipHandler {

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent e) {
		if (e.getItemStack().getItem() == Items.SHIELD)
			e.getToolTip().add(BetterShields.getDamageReductionTextComponent(Config.defaultDamageReduction.get()));
		else if (e.getItemStack().getItem() instanceof ShieldItem
				&& !(e.getItemStack().getItem() instanceof BetterShieldItem))
			e.getToolTip().add(BetterShields.getDamageReductionTextComponent(
					Config.customShieldMaxReduction.get() ? 100 : Config.defaultDamageReduction.get()));
	}

}