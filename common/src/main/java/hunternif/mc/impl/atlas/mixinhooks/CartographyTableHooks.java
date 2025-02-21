package hunternif.mc.impl.atlas.mixinhooks;

import hunternif.mc.api.AtlasAPI;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.item.AtlasItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CartographyTableHooks {
    public static void onTakeItem(PlayerEntity player, ItemStack map, ItemStack atlas) {
        if (player.getEntityWorld().isClient()) {
            return;
        }

        if (map.getItem() == Items.FILLED_MAP) {
            MapState mapState = FilledMapItem.getMapState(map, player.getEntityWorld());
            if (mapState != null) {
                mapState.getDecorations().forEach(icon -> {
                    int i = 1 << mapState.scale;

                    int x = (int) ((int) (icon.x() - 0.5f) / 2f) * i + mapState.centerX;
                    int z = (int) ((int) (icon.z() - 0.5f) / 2f) * i + mapState.centerZ;

                    Identifier type = null;
                    Text label = null;

                    if (icon.type().matches(MapDecorationTypes.RED_X)) {
                        type = AntiqueAtlasMod.id("red_x_small");
                        label = Text.translatable("gui.antiqueatlas.marker.treasure");
                    } else if (icon.type().matches(MapDecorationTypes.MONUMENT)) {
                        type = AntiqueAtlasMod.id("monument");
                        label = Text.translatable("gui.antiqueatlas.marker.monument");
                    } else if (icon.type().matches(MapDecorationTypes.MANSION)) {
                        type = AntiqueAtlasMod.id("mansion");
                        label = Text.translatable("gui.antiqueatlas.marker.mansion");
                    }

                    if (type != null) {
                        AtlasAPI.getMarkerAPI().putMarker(player.getEntityWorld(), true, AtlasItem.getAtlasID(atlas), type, label, x, z);
                    }
                });
            }
        }
    }
}
