package com.tome.bettershields.client;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;

public class ShieldModel implements IBakedModel {

	private final IBakedModel original;

	public ShieldModel(IBakedModel originalModel) {
		original = originalModel;
	}

	@Deprecated
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return original.getQuads(state, side, rand);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
		return original.getQuads(state, side, rand, extraData);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Deprecated
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return original.getParticleTexture();
	}

	@Override
	public TextureAtlasSprite getParticleTexture(IModelData data) {
		return original.getParticleTexture(data);
	}

	@Override
	public ItemOverrideList getOverrides() {
		return original.getOverrides();
	}

	@Deprecated
	@Override
	public net.minecraft.client.renderer.model.ItemCameraTransforms getItemCameraTransforms() {
		return original.getItemCameraTransforms();
	}

}
