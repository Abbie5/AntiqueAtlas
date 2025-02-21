package hunternif.mc.impl.atlas.network.packet.s2c.play;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.AntiqueAtlasModClient;
import hunternif.mc.impl.atlas.marker.Marker;
import hunternif.mc.impl.atlas.marker.MarkersData;
import hunternif.mc.impl.atlas.network.packet.s2c.S2CPacket;
import hunternif.mc.impl.atlas.registry.MarkerType;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sends markers set via API from server to client.
 * Only one dimension per packet.
 * The markers in one packet are either all global or all local.
 *
 * @author Hunternif
 * @author Haven King
 */
public record PutMarkersS2CPacket(
        int atlasID,
        RegistryKey<World> world,
        ListMultimap<Identifier, Marker.Precursor> markersByType
) implements S2CPacket {
    public static final Id<PutMarkersS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "marker", "put"));
    public static final PacketCodec<ByteBuf, PutMarkersS2CPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, PutMarkersS2CPacket::atlasID,
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), PutMarkersS2CPacket::world,
            PacketCodecs.map(HashMap::new, Identifier.PACKET_CODEC, Marker.Precursor.PACKET_CODEC.collect(PacketCodecs.toList()))
                    .xmap(map -> {
                        ListMultimap<Identifier, Marker.Precursor> multimap = ArrayListMultimap.create();
                        for (Map.Entry<Identifier, List<Marker.Precursor>> entry : map.entrySet()) {
                            multimap.putAll(entry.getKey(), entry.getValue());
                        }
                        return multimap;
                    }, multimap -> {
                        HashMap<Identifier, List<Marker.Precursor>> map = new HashMap<>();
                        for (var key : multimap.keySet()) {
                            map.put(key, multimap.get(key));
                        }
                        return map;
                    }), PutMarkersS2CPacket::markersByType,
            PutMarkersS2CPacket::new
    );

    private static final int GLOBAL = -1;

    public PutMarkersS2CPacket(int atlasID, RegistryKey<World> world, Collection<Marker> markers) {
        this(atlasID, world, collectMarkers(markers));
    }
    
    private static ListMultimap<Identifier, Marker.Precursor> collectMarkers(Collection<Marker> markers) {
        ListMultimap<Identifier, Marker.Precursor> markersByType = ArrayListMultimap.create();
        for (Marker marker : markers) {
            markersByType.put(marker.getType(), marker.getPrecursor());
        }
        return markersByType;
    }

    @Override
    public Id<?> getId() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    public static void apply(PutMarkersS2CPacket packet, NetworkManager.PacketContext context) {
        context.queue(() -> {
            MarkersData markersData = packet.atlasID == GLOBAL
                    ? AntiqueAtlasMod.globalMarkersData.getData()
                    : AntiqueAtlasMod.markersData.getMarkersDataCached(packet.atlasID, packet.world);

            for (Identifier type : packet.markersByType.keys()) {
                MarkerType markerType = MarkerType.REGISTRY.get(type);
                for (Marker.Precursor precursor : packet.markersByType.get(type)) {
                    markersData.loadMarker(new Marker(MarkerType.REGISTRY.getId(markerType), packet.world, precursor));
                }
            }

            AntiqueAtlasModClient.getAtlasGUI().updateBookmarkerList();
        });
    }
}
