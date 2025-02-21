package hunternif.mc.impl.atlas.marker;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

/** Holds global markers, i.e. ones that appear in all atlases. */
public class GlobalMarkersData extends MarkersData {
	
	public static final Type<GlobalMarkersData> TYPE = new Type<>(
			GlobalMarkersData::new,
			GlobalMarkersData::readNbt,
			DataFixTypes.LEVEL
	);

	public GlobalMarkersData() {
	}

	@Override
	public Marker createAndSaveMarker(Identifier type, RegistryKey<World> world, int x, int y, boolean visibleAhead, Text label) {
		return super.createAndSaveMarker(type, world, x, y, visibleAhead, label).setGlobal(true);
	}

	public static GlobalMarkersData readNbt(NbtCompound compound, RegistryWrapper.WrapperLookup lookup) {
		GlobalMarkersData data = new GlobalMarkersData();
		doReadNbt(compound, data, lookup);
		return data;
	}

	@Override
	public Marker loadMarker(Marker marker) {
		return super.loadMarker(marker).setGlobal(true);
	}

	/** Send all data to the player in several packets. */
    void syncOnPlayer(ServerPlayerEntity player) {
		syncToPlayer(-1, player);
	}
}
