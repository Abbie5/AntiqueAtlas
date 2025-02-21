package hunternif.mc.impl.atlas.client.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class TileTextureMapImpl {
    public static boolean biomeIsVoid(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_VOID);
    }

    public static boolean biomeIsEnd(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_END) || biomeTag.isIn(ConventionalBiomeTags.IS_OUTER_END_ISLAND);
    }

    public static boolean biomeHasVegetation(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_VEGETATION_DENSE) || biomeTag.isIn(ConventionalBiomeTags.IS_VEGETATION_SPARSE);
    }

    public static boolean biomeIsNether(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_NETHER);
    }

    public static boolean biomeIsSwamp(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_SWAMP);
    }

    public static boolean biomeIsWater(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_AQUATIC);
    }

    public static boolean biomeIsIcy(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_ICY);
    }

    public static boolean biomeIsShore(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_BEACH);
    }

    public static boolean biomeIsJungle(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_JUNGLE) || biomeTag.isIn(ConventionalBiomeTags.IS_JUNGLE_TREE);
    }

    public static boolean biomeIsSavanna(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_SAVANNA) || biomeTag.isIn(ConventionalBiomeTags.IS_SAVANNA_TREE);
    }

    public static boolean biomeIsBadlands(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_BADLANDS);
    }

    public static boolean biomeIsPlateau(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_plateau"))); // not present in ConventionalBiomeTags for some reason
    }

    public static boolean biomeIsForest(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_FOREST);
    }

    public static boolean biomeIsSnowy(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_SNOWY);
    }

    public static boolean biomeIsPlains(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_PLAINS) || biomeTag.isIn(ConventionalBiomeTags.IS_SNOWY_PLAINS);
    }

    public static boolean biomeIsDesert(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_DESERT);
    }

    public static boolean biomeIsTaiga(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_TAIGA);
    }

    public static boolean biomeIsExtremeHills(RegistryEntry<Biome> biomeTag) {
        return false; // None
    }

    public static boolean biomeIsPeak(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_MOUNTAIN_PEAK);
    }

    public static boolean biomeIsMountain(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_MOUNTAIN) || biomeTag.isIn(ConventionalBiomeTags.IS_MOUNTAIN_SLOPE);
    }

    public static boolean biomeIsMushroom(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_MUSHROOM);
    }

    public static boolean biomeIsUnderground(RegistryEntry<Biome> biomeTag) {
        return biomeTag.isIn(ConventionalBiomeTags.IS_UNDERGROUND);
    }
}
