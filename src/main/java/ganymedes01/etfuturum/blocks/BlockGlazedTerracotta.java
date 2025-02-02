package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.RenderIDs;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGlazedTerracotta extends BaseBlock {

	private final int meta;
	@SideOnly(Side.CLIENT)
	private IIcon blockIconFlipped;

	public BlockGlazedTerracotta(int meta) {
		super(Material.rock);
		setHardness(1.4F);
		setResistance(1.4F);
		setNames(ModRecipes.dye_names[meta] + "_glazed_terracotta");
		this.meta = meta;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
		int direction = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
	}

	@Override
	public MapColor getMapColor(int _meta) {
		return MapColor.getMapColorForBlockColored(meta);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 0:
				return blockIconFlipped;
			case 2:
				return meta % 2 == 0 ? blockIconFlipped : blockIcon;
			case 5:
				return meta % 2 == 1 ? blockIconFlipped : blockIcon;
		}
		return this.blockIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		super.registerBlockIcons(p_149651_1_);
		blockIconFlipped = new IconFlipped(blockIcon, true, false);
	}

	//	@Override
//	public void registerBlockIcons(IIconRegister p_149651_1_) {
//		super.registerBlockIcons(p_149651_1_);
//		blockIconFlipped = new IconFlipped(blockIcon, true, false);
//	}
//
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return RenderIDs.GLAZED_TERRACOTTA;
	}
}
