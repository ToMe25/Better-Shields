package com.tome.bettershields;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE, modid = BetterShields.MODID)
public class BetterShieldItem extends ShieldItem {

	private Supplier<Integer> damageReduction;
	@SuppressWarnings("deprecation")
	private LazyLoadedValue<Ingredient> repairMaterial;

	public BetterShieldItem(String registryName, ConfigValue<Integer> damageReduction, String repairTag, int durability,
			boolean fireProof) {
		this(new ResourceLocation(BetterShields.MODID, registryName), damageReduction::get,
				() -> getTagIngredient(repairTag), durability, fireProof);
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

	/**
	 * Required for one jar to work in 1.18.1 and 1.18.2.
	 * A reflection based hack that should not be used unless necessary.
	 * 
	 * @param name	The name of the item tag to get.
	 * @return	The ingredient representing the item tag for the given name.
	 */
	private static Ingredient getTagIngredient(String name) {
		Ingredient ingredient = null;

		try {
			final Method getAllTags = ObfuscationReflectionHelper.findMethod(ItemTags.class, "m_13193_");
			final Object allTags = getAllTags.invoke(null);
			final Class<?> TagCollection = Class.forName("net.minecraft.tags.TagCollection");
			final Method getTag = ObfuscationReflectionHelper.findMethod(TagCollection, "m_13404_", ResourceLocation.class);
			Object tag = getTag.invoke(allTags, new ResourceLocation(name));	
			final Class<?> Tag = Class.forName("net.minecraft.tags.Tag");
			final Method of = ObfuscationReflectionHelper.findMethod(Ingredient.class, "m_43911_", Tag);
			ingredient = (Ingredient) of.invoke(null, tag);
		} catch (ObfuscationReflectionHelper.UnableToFindMethodException e) {
			// Seems like we are in 1.18.2
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (ingredient == null) {// Seems like we are in 1.18.2
			try {
				final Method bind = ObfuscationReflectionHelper.findMethod(ItemTags.class, "m_203854_", String.class);
				Object tag = bind.invoke(null, name);
				final Class<?> TagKey = Class.forName("net.minecraft.tags.TagKey");
				final Method of = ObfuscationReflectionHelper.findMethod(Ingredient.class, "m_204132_", TagKey);
				ingredient = (Ingredient) of.invoke(null, tag);
			} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return ingredient;
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
