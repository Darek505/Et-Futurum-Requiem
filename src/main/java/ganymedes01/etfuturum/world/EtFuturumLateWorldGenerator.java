package ganymedes01.etfuturum.world;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockNewStone;
import ganymedes01.etfuturum.blocks.BlockTuff;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.WorldGenDeepslateBlob;
import ganymedes01.etfuturum.world.generate.WorldGenTuffBlob;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;

public class EtFuturumLateWorldGenerator extends EtFuturumWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (ConfigBlocksItems.enableCoarseDirt && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
			//TODO Add checks so it doesn't run this code in biomes that don't generate coarse dirt
			for (int x = chunkX * 16; x < (chunkX * 16) + 16; x++) {
				for (int z = chunkZ * 16; z < (chunkZ * 16) + 16; z++) {
					for (int y = 0; y < world.getActualHeight(); y++) {
						if (world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 1) {
							world.setBlock(x, y, z, ModBlocks.coarse_dirt, 0, 2);
						}
					}
				}
			}
		}
		
		if(world.getWorldInfo().getTerrainType() != WorldType.FLAT || world.getWorldInfo().getGeneratorOptions().contains("decoration") || world.provider.dimensionId != 0) {
			if(ConfigBlocksItems.enableDeepslate && ConfigWorld.deepslateGenerationMode == 1 && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				int x = chunkX * 16 + rand.nextInt(16);
				int z = chunkZ * 16 + rand.nextInt(16);
				new WorldGenDeepslateBlob(ConfigWorld.maxDeepslatePerCluster).generate(world, rand, x, rand.nextInt(ConfigWorld.deepslateMaxY), z);
			}
			if(ConfigBlocksItems.enableTuff && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				int x = chunkX * 16 + rand.nextInt(16);
				int z = chunkZ * 16 + rand.nextInt(16);
				new WorldGenTuffBlob(ConfigWorld.maxTuffPerCluster).generate(world, rand, x, rand.nextInt(ConfigWorld.deepslateMaxY), z);
			}
		}
		
		if (!ConfigMixins.deepslateLayerOptimization && ConfigBlocksItems.enableDeepslate && ConfigWorld.deepslateGenerationMode == 0 && ConfigWorld.deepslateMaxY > 0 && world.getWorldInfo().getTerrainType() != WorldType.FLAT && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, world.provider.dimensionId)) {
			for (int x = chunkX * 16; x < (chunkX * 16) + 16; x++) {
				for (int z = chunkZ * 16; z < (chunkZ * 16) + 16; z++) {
					for (int y = 0; y <= ConfigWorld.deepslateMaxY; y++) {
						Block block = world.getBlock(x, y, z);
						if(y < ConfigWorld.deepslateMaxY - 4 || world.rand.nextInt(y > ConfigWorld.deepslateMaxY - 2 ? 3 : 2) == 0) {
							BlockAndMetadataMapping mapping = EtFuturum.deepslateOres.get(new BlockAndMetadataMapping(block, world.getBlockMetadata(x, y, z)));
							if(mapping != null) {
								world.setBlock(x, y, z, mapping.getOre(), mapping.getMeta(), 2);
							} else
							if((ConfigWorld.deepslateReplacesDirt && block.isReplaceableOreGen(world, x, y, z, Blocks.dirt))
									|| (block.isReplaceableOreGen(world, x, y, z, Blocks.stone) && (ConfigWorld.deepslateReplacesStones ? true : !(block instanceof BlockNewStone)))
									&& !(block instanceof BlockTuff)) {
								world.setBlock(x, y, z, ModBlocks.deepslate, 0, 2);
							} else if(ConfigTweaks.deepslateReplacesCobblestone && (block.isReplaceableOreGen(world, x, y, z, Blocks.cobblestone))) {
								world.setBlock(x, y, z, ModBlocks.cobbled_deepslate, 0, 2);
							}
						}
					}
				}
			}
		}
	}
}
