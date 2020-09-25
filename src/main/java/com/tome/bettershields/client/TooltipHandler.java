package com.tome.bettershields.client;

import java.util.List;

import com.tome.bettershields.BetterShieldItem;
import com.tome.bettershields.BetterShields;
import com.tome.bettershields.Config;

import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = BetterShields.MODID)
public class TooltipHandler {

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent e) {
		List<ITextComponent> tooltip = e.getToolTip();
		if (e.getItemStack().getItem() == Items.SHIELD) {
			tooltip.add(new StringTextComponent(""));
			tooltip.add(BetterShields.getBlockingTextComponent());
			tooltip.add(BetterShields.getDamageReductionTextComponent(Config.defaultDamageReduction.get()));
		} else if (e.getItemStack().getItem() instanceof ShieldItem
				&& !(e.getItemStack().getItem() instanceof BetterShieldItem)) {
			tooltip.add(new StringTextComponent(""));
			tooltip.add(BetterShields.getBlockingTextComponent());
			tooltip.add(BetterShields.getDamageReductionTextComponent(
					Config.customShieldMaxReduction.get() ? 100 : Config.defaultDamageReduction.get()));
		}
	}

}