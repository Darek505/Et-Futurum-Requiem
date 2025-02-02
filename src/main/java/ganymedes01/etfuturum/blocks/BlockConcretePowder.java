package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConcretePowder extends BaseSubtypesSand {

	public BlockConcretePowder() {
		super(Material.sand, "white_concrete_powder", "orange_concrete_powder", "magenta_concrete_powder", "light_blue_concrete_powder", "yellow_concrete_powder",
				"lime_concrete_powder", "pink_concrete_powder", "gray_concrete_powder", "light_gray_concrete_powder", "cyan_concrete_powder", "purple_concrete_powder",
				"blue_concrete_powder", "brown_concrete_powder", "green_concrete_powder", "red_concrete_powder", "black_concrete_powder");
		this.setHarvestLevel("shovel", 0);
		this.setStepSound(soundTypeSand);
		this.setHardness(.5F);
		this.setResistance(.5F);
		this.setBlockName(Utils.getUnlocalisedName("concrete_powder"));
		this.setBlockTextureName("concrete_powder");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		this.setBlock(world, x, y, z);
	}
			
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		this.setBlock(world, x, y, z);
	}
	
	private void setBlock(World world, int x, int y, int z) {
		for (ForgeDirection dir : ForgeDirection.values()) {
			if (dir != ForgeDirection.DOWN && world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.water) {
				int l = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, ModBlocks.CONCRETE.get(), l, 2);
				world.markBlockForUpdate(x, y, z);
			}
		}
		world.markBlockForUpdate(x, y, z);
	}

	public MapColor getMapColor(int p_149728_1_) {
		return MapColor.getMapColorForBlockColored(p_149728_1_);
	}

}
