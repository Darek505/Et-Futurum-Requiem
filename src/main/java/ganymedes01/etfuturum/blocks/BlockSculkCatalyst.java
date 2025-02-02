package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntitySculkCatalyst;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSculkCatalyst extends BaseBlock {
	private IIcon topIcon, bottomIcon, sideBloomIcon, topBloomIcon;

	public BlockSculkCatalyst() {
		super(Material.ground);
		setNames("sculk_catalyst");
		setHardness(3.0F);
		setResistance(3.0F);
		Utils.setBlockSound(this, ModSounds.soundSculkCatalyst);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(getTextureName() + "_side");
		this.topIcon = reg.registerIcon(getTextureName() + "_top");
		this.bottomIcon = reg.registerIcon(getTextureName() + "_bottom");
		this.sideBloomIcon = reg.registerIcon(getTextureName() + "_side_bloom");
		this.topBloomIcon = reg.registerIcon(getTextureName() + "_top_bloom");
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 20;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		boolean bloom = meta == 1;
		if(side == 1) {
			return bloom ? topBloomIcon : topIcon;
		} else if(side == 0) {
			return bottomIcon;
		} else {
			return bloom ? sideBloomIcon : blockIcon;
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntitySculkCatalyst();
	}
}
