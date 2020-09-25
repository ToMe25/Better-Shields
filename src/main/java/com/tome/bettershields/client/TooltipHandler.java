package com.tome.bettershields.client;

import java.util.List;

import com.tome.bettershields.BetterShieldItem;
import com.tome.bettershields.BetterShields;
import com.tome.bettershields.Config;

import net.minecraft.item.Item;
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
		if (e.getItemStack().getItem() instanceof ShieldItem) {
			Item shield = e.getItemStack().getItem();
			List<ITextComponent> tooltip = e.getToolTip();
			tooltip.add(new StringTextComponent(""));
			tooltip.add(BetterShields.getBlockingTextComponent());
			if (shield == Items.SHIELD) {
				tooltip.add(BetterShields.getDamageReductionTextComponent(Config.defaultDamageReduction.get()));
			} else if (shield instanceof BetterShieldItem) {
				tooltip.add(BetterShields
						.getDamageReductionTextComponent(((BetterShieldItem) shield).getDamageReduction()));
			} else {
				tooltip.add(BetterShields.getDamageReductionTextComponent(
						Config.customShieldMaxReduction.get() ? 100 : Config.defaultDamageReduction.get()));
			}
		}
	}

}