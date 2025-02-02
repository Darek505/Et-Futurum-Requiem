package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockWoodPressurePlate extends BlockPressurePlate {

	private final int meta;

	public BlockWoodPressurePlate(int meta) {
		super("planks_oak", Material.wood, Sensitivity.everything);
		this.meta = meta;
		disableStats();
		setHardness(0.5F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName("pressure_plate_" + BlockWoodDoor.names[meta]));
		setCreativeTab(EtFuturum.creativeTabBlocks);
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int _meta) {
		return Blocks.planks.getIcon(side, this.meta);
	}
}