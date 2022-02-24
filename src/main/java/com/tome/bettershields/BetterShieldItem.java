package com.tome.bettershields;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.tome.bettershields.client.ShieldTileEntityRenderer;

import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE, modid = BetterShields.MODID)
public class BetterShieldItem extends ShieldItem {

	private Supplier<Integer> damageReduction;
	private LazyLoadedValue<Ingredient> repairMaterial;

	public BetterShieldItem(String registryName, Supplier<Integer> damageReduction, Tag<Item> repairMaterial,
			int durability, boolean fireProof) {
		this(new ResourceLocation(BetterShields.MODID, registryName), damageReduction,
				() -> Ingredient.of(repairMaterial), durability, fireProof);
	}

	@SuppressWarnings("deprecation")
	public BetterShieldItem(ResourceLocation registryName, Supplier<Integer> damageReduction,
			Supplier<Ingredient> repairMaterial, int durability, boolean fireProof) {
		super((fireProof ? new Properties().fireResistant() : new Properties()).tab(CreativeModeTab.TAB_COMBAT)
				.durability(durability));
		setRegistryName(registryName);
		this.damageReduction = damageReduction;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return ShieldTileEntityRenderer.instance;
			}
		});
	}

	/**
	 * Gets the percentage of the damage received this shield blocks.
	 * 
	 * @return The percentage of the damage received this shield blocks.
	 */
	public int getDamageReduction() {
		return damageReduction.get();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repairMaterial.get().test(repair);
	}

	@SubscribeEvent
	public static void onShieldBlock(ShieldBlockEvent e) {
		if (Config.enableDamageReduction.get()) {
			float damage = e.getOriginalBlockedDamage();
			LivingEntity victim = e.getEntityLiving();
			DamageSource source = e.getDamageSource();
			
			if (source.isProjectile()) {
				return;
			}

			float f1 = 0.0f;
			if (damage > 0.0F && victim.isDamageSourceBlocked(source)) {
				f1 = damage;
				float reduction = 1f;

				Item shield = victim.getUseItem().getItem();
				if (shield instanceof BetterShieldItem) {
					reduction = ((BetterShieldItem) shield).getDamageReduction() / 100f;
				} else if (shield == Items.SHIELD || (!Config.customShieldMaxReduction.get()
						&& victim.getUseItem().getUseAnimation() == UseAnim.BLOCK)) {
					reduction = Config.defaultDamageReduction.get() / 100f;
				}

				if (reduction < 1f) {
					f1 = damage * reduction;
				}

				int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.THORNS, victim.getUseItem());
				if (level > 0 && reduction == 1f) {
					Enchantments.THORNS.doPostHurt(victim, source.getEntity(), level);
				}
			}
			e.setBlockedDamage(f1);
		}
	}

}
