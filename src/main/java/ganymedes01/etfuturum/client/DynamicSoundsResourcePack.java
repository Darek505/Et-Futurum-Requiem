package ganymedes01.etfuturum.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.lib.Reference;
import makamys.mclib.json.JsonUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class DynamicSoundsResourcePack implements IResourcePack {

	@Override
	public Set<String> getResourceDomains() {
		// No modded namespace support for now
		return ImmutableSet.of("minecraft");
	}

	@Override
	public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() {
		return null;
	}

	@Override
	public String getPackName() {
		return "Et Futurum Requiem dynamic sounds.json and lang";
	}

	public InputStream getInputStream(ResourceLocation resLoc) throws IOException {
		if(resLoc.getResourcePath().equals("sounds.json")) {
			return new ByteArrayInputStream(new JsonCreator().getJson().toString().getBytes());
		}
		return null;
	}

	public boolean resourceExists(ResourceLocation resLoc) {
		return resLoc.getResourcePath().equals("sounds.json")/* || resLoc.getResourcePath().endsWith("lang")*/;
	}

	private boolean resourceExistsSomewhere(ResourceLocation resLoc) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(resLoc) != null;
		} catch (IOException e) {}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void inject() {
		if (ConfigSounds.caveAmbience) {
			IResourcePack dynamicResourcePack = new DynamicSoundsResourcePack();
			Minecraft.getMinecraft().defaultResourcePacks.add(dynamicResourcePack);
			IResourceManager resMan = Minecraft.getMinecraft().getResourceManager();
			if (resMan instanceof SimpleReloadableResourceManager) {
				((SimpleReloadableResourceManager) resMan).reloadResourcePack(dynamicResourcePack);
			}
		}
	}

	public class JsonCreator {
		private final JsonObject rootObject = new JsonObject();

		private void setReplace(String cat) {
			JsonObject soundCat = JsonUtil.getOrCreateObject(rootObject, cat);
			soundCat.add("replace", new JsonPrimitive(true));
		}

		private void addSoundsToCategory(String cat, String... sounds) {
			JsonObject soundCat = JsonUtil.getOrCreateObject(rootObject, cat);
			JsonArray soundList = JsonUtil.getOrCreateArray(soundCat, "sounds");
			for (String sound : sounds) {
				soundList.add(new JsonPrimitive(sound));
			}
		}

		private void addSoundEventsToCategory(String cat, String... sounds) {
			addSoundToCategoryWithSettings(cat, 1.0F, 1.0F, 1, false, true, sounds);
		}

		private void addSoundToCategoryWithSettings(String cat, float volume, float pitch, String... sounds) {
			addSoundToCategoryWithSettings(cat, volume, pitch, 1, false, false, sounds);
		}

		private void addSoundToCategoryWithSettings(String cat, float volume, float pitch, int weight, String... sounds) {
			addSoundToCategoryWithSettings(cat, volume, pitch, weight, false, false, sounds);
		}

		private void addSoundToCategoryWithSettings(String cat, float volume, float pitch, int weight, boolean stream, boolean isEvent, String... sounds) {
			JsonObject soundCat = JsonUtil.getOrCreateObject(rootObject, cat);
			JsonArray soundList = JsonUtil.getOrCreateArray(soundCat, "sounds");
			for(String sound : sounds) {
				JsonObject soundObj = new JsonObject();
				soundObj.add("name", new JsonPrimitive(sound));
				if(volume != 1.0F) {
					soundObj.add("volume", new JsonPrimitive(MathHelper.clamp_float(volume, 0, 1)));
				}
				if(pitch != 1.0F) {
					soundObj.add("pitch", new JsonPrimitive(pitch));
				}
				if(weight > 1) {
					soundObj.add("weight", new JsonPrimitive(weight));
				}
				if(stream) {
					soundObj.add("stream", new JsonPrimitive(true));
				}
				if(isEvent) {
					soundObj.add("type", new JsonPrimitive("event"));
				}
				soundList.add(soundObj);
			}
		}

		public JsonObject getJson() {
			if(ConfigSounds.caveAmbience) {
				addSoundsToCategory("ambient.cave.cave",
						Reference.MCAssetVer + ":ambient/cave/cave14",
						Reference.MCAssetVer + ":ambient/cave/cave15",
						Reference.MCAssetVer + ":ambient/cave/cave16",
						Reference.MCAssetVer + ":ambient/cave/cave17",
						Reference.MCAssetVer + ":ambient/cave/cave18",
						Reference.MCAssetVer + ":ambient/cave/cave19");
			}
			return rootObject;
		}
	}
}
