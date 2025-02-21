package hunternif.mc.impl.atlas.client.neoforge;

import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

@OnlyIn(Dist.CLIENT)
public class TileTextureMapImpl {
    public static boolean biomeIsVoid(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_VOID);
    }

    public static boolean biomeIsEnd(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_END) || biomeTag.isIn(Tags.Biomes.IS_OUTER_END_ISLAND);
    }

    public static boolean biomeHasVegetation(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_SPARSE_VEGETATION) || biomeTag.isIn(Tags.Biomes.IS_DENSE_VEGETATION); // Not 100% sold here
    }

    public static boolean biomeIsNether(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_NETHER);
    }

    public static boolean biomeIsSwamp(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_SWAMP);
    }

    public static boolean biomeIsWater(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_AQUATIC);
    }

    public static boolean biomeIsIcy(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_ICY);
    }

    public static boolean biomeIsShore(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(BiomeTags.IS_BEACH);
    }

    public static boolean biomeIsJungle(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_JUNGLE) || biomeTag.isIn(Tags.Biomes.IS_JUNGLE_TREE);
    }

    public static boolean biomeIsSavanna(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_SAVANNA) || biomeTag.isIn(Tags.Biomes.IS_SAVANNA_TREE);
    }

    public static boolean biomeIsBadlands(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_BADLANDS);
    }

    public static boolean biomeIsPlateau(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_PLATEAU);
    }

    public static boolean biomeIsForest(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_FOREST);
    }

    public static boolean biomeIsSnowy(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_SNOWY);
    }

    public static boolean biomeIsPlains(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_PLAINS) || biomeTag.isIn(Tags.Biomes.IS_SNOWY_PLAINS);
    }

    public static boolean biomeIsDesert(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_DESERT);
    }

    public static boolean biomeIsTaiga(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_TAIGA);
    }

    public static boolean biomeIsExtremeHills(RegistryEntry<Biome> biomeTag) {
        return false; // None
    }

    public static boolean biomeIsPeak(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_MOUNTAIN_PEAK);
    }

    public static boolean biomeIsMountain(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_MOUNTAIN) || biomeTag.isIn(Tags.Biomes.IS_MOUNTAIN_SLOPE);
    }

    public static boolean biomeIsMushroom(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_MUSHROOM);
    }

    public static boolean biomeIsUnderground(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(Tags.Biomes.IS_UNDERGROUND);
    }
}
