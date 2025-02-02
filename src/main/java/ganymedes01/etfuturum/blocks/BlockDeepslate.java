package ganymedes01.etfuturum.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockDeepslate extends BlockRotatedPillar {

	public BlockDeepslate() {
		super(Material.rock);
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate"));
		this.setBlockTextureName("deepslate");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
		Utils.setBlockSound(this, ModSounds.soundDeepslate);
	}

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_)
	{
		return field_150164_N;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = p_149651_1_.registerIcon(getTextureName());
		field_150164_N = p_149651_1_.registerIcon(getTextureName() + "_top");
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(ModBlocks.COBBLED_DEEPSLATE.get());
	}

	@Override
	/**
	 * We need to do this in order to make sure deepslate generates, as veins crossing a chunk border may not get replaced fully.
	 * This is used to detect those ores since they'd generate over deepslate, not the other way around.
	 */
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		boolean flag = target == Blocks.stone || this == target;
		if(flag) {
			doDeepslateRedoCheck(world, x, y, z);
		}
		return flag;
	}
	
	public static void doDeepslateRedoCheck(World world, int x, int y, int z) {
		if(!EtFuturumLateWorldGenerator.stopRecording && !world.getChunkFromBlockCoords(x, z).sendUpdates) {
			Map<Long, List<Integer>> map = EtFuturumLateWorldGenerator.deepslateRedoCache.computeIfAbsent(world.provider.dimensionId, k -> Maps.newConcurrentMap());
			List<Integer> posSet = map.computeIfAbsent(ChunkCoordIntPair.chunkXZ2Int(x >> 4, z >> 4), k -> Lists.newLinkedList());
			posSet.add((x & 0xF) << 12 | (y & 0xFF) << 4 | (z & 0xF));
		}
	}
}
