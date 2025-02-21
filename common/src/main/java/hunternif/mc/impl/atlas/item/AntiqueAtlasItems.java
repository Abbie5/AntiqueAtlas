package hunternif.mc.impl.atlas.item;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;

public class AntiqueAtlasItems {
    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(AntiqueAtlasMod.ID, RegistryKeys.ITEM);
    public static final DeferredRegister<RecipeSerializer<?>>
            RECIPES = DeferredRegister.create(AntiqueAtlasMod.ID, RegistryKeys.RECIPE_SERIALIZER);
    public static final DeferredRegister<ComponentType<?>>
            COMPONENTS = DeferredRegister.create(AntiqueAtlasMod.ID, RegistryKeys.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<Item> EMPTY_ATLAS = ITEMS.register("empty_antique_atlas", () -> new EmptyAtlasItem(new Item.Settings()));
    public static final RegistrySupplier<Item> ATLAS = ITEMS.register("antique_atlas", () -> new AtlasItem(new Item.Settings().maxCount(1)));
    
    public static final RegistrySupplier<ComponentType<Integer>> ATLAS_COMPONENT = COMPONENTS.register("atlas", () -> ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build());

    public static ItemStack getAtlasFromId(int atlasID) {
        ItemStack atlas = new ItemStack(ATLAS.get());
        atlas.set(ATLAS_COMPONENT.get(), atlasID);

        return atlas;
    }

    public static void register() {
        if (AntiqueAtlasMod.CONFIG.itemNeeded) {
            COMPONENTS.register();
            ITEMS.register();

            RECIPES.register("atlas_clone", () -> RecipeAtlasCloning.SERIALIZER);
            RECIPES.register("atlas_combine", () -> RecipeAtlasCombining.SERIALIZER);

            RECIPES.register();
        }
    }
}
