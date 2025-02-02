package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockNewLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodBarkNew extends BlockNewLog implements ISubBlocksBlock {
	public static final String[] types = new String[]{"acacia_wood", "dark_oak_wood"};

	public BlockWoodBarkNew() {
		setBlockName(Utils.getUnlocalisedName("bark2"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public IIcon[] getIcons() {
		return field_150167_a;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getTypes()[stack.getItemDamage() % getTypes().length];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.field_150167_a = new IIcon[field_150169_M.length];
		this.field_150166_b = new IIcon[field_150169_M.length];
		for (int i = 0; i < field_150169_M.length; i++) {
			field_150167_a[i] = field_150166_b[i] = Blocks.log2.getIcon(2, i);
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return true;
	}
	
	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 5;
	}
}
