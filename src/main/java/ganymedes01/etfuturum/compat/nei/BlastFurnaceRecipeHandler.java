package ganymedes01.etfuturum.compat.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import ganymedes01.etfuturum.client.gui.inventory.GuiBlastFurnace;
import ganymedes01.etfuturum.core.utils.ItemStackMap;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class BlastFurnaceRecipeHandler extends FurnaceRecipeHandler {

	private final ArrayList<CachedRecipe> basecache = new ArrayList<>();
	private final ArrayList<CachedRecipe> recipecache = new ArrayList<>();
	private final ArrayList<CachedRecipe> usagecache = new ArrayList<>();

	@Override
	public void loadTransferRects() {
		// Location of the "Recipes" Button on the Progress Bar of the Furnace.
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getOverlayIdentifier()));
		// Location of the "Fuel" Button on the Fuel Bar of the Furnace.
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getOverlayIdentifier())) {
			if(basecache.isEmpty()) {
				Map<ItemStack, ItemStack> furnaceSmeltingList = FurnaceRecipes.smelting().getSmeltingList();
				ItemStackMap<ItemStack> recipes = new ItemStackMap<>();
				for (Map.Entry<ItemStack, ItemStack> entry : furnaceSmeltingList.entrySet()) {
					if (BlastFurnaceRecipes.smelting().canAdd(entry.getKey(), entry.getValue()))
						recipes.put(entry.getKey(), entry.getValue());
				}
				recipes.putAll(BlastFurnaceRecipes.smelting().smeltingList);

				recipes.entrySet().removeIf(stack -> BlastFurnaceRecipes.smelting().smeltingBlacklist.contains(stack.getKey()));

				for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
					basecache.addAll(arecipes);
				}
			} else {
				arecipes.addAll(basecache);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (recipecache.isEmpty()) {
			Map<ItemStack, ItemStack> furnaceSmeltingList = FurnaceRecipes.smelting().getSmeltingList();
			ItemStackMap<ItemStack> recipes = new ItemStackMap<>();
			for (Map.Entry<ItemStack, ItemStack> entry : furnaceSmeltingList.entrySet()) {
				if (BlastFurnaceRecipes.smelting().canAdd(entry.getKey(), entry.getValue()))
					recipes.put(entry.getKey(), entry.getValue());
			}
			recipes.putAll(BlastFurnaceRecipes.smelting().smeltingList);

			recipes.entrySet().removeIf(stack -> BlastFurnaceRecipes.smelting().smeltingBlacklist.contains(stack.getKey()));

			for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
				if (NEIServerUtils.areStacksSameType(recipe.getValue(), result)) {
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
					recipecache.addAll(arecipes);
				}
			}
		} else {
			arecipes.addAll(recipecache);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (usagecache.isEmpty()) {
			Map<ItemStack, ItemStack> furnaceSmeltingList = FurnaceRecipes.smelting().getSmeltingList();
			ItemStackMap<ItemStack> recipes = new ItemStackMap<>();
			for (Map.Entry<ItemStack, ItemStack> entry : furnaceSmeltingList.entrySet()) {
				if (BlastFurnaceRecipes.smelting().canAdd(entry.getKey(), entry.getValue()))
					recipes.put(entry.getKey(), entry.getValue());
			}
			recipes.putAll(BlastFurnaceRecipes.smelting().smeltingList);

			recipes.entrySet().removeIf(stack -> BlastFurnaceRecipes.smelting().smeltingBlacklist.contains(stack.getKey()));

			for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
				if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
					SmeltingPair arecipe = new SmeltingPair(recipe.getKey(), recipe.getValue());
					//arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
					arecipes.add(arecipe);
				}
				usagecache.addAll(arecipes);
			}
		} else {
			arecipes.addAll(usagecache);
		}
	}

	@Override
	public String getRecipeName() {
		return I18n.format("efr.nei.blast_furnace");
	}

	@Override
	public String getOverlayIdentifier() {
		return "etfuturum.blastfurnace";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiBlastFurnace.class;
	}

	public void clearCache() {
		basecache.clear();
		recipecache.clear();
		usagecache.clear();
	}
}
