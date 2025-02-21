package hunternif.mc.impl.atlas.core;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class TileInfo {
    public static final PacketCodec<ByteBuf, TileInfo> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, t -> t.x,
            PacketCodecs.VAR_INT, t -> t.z,
            Identifier.PACKET_CODEC, t -> t.id,
            TileInfo::new
    );

    public final int x, z;
    public final Identifier id;

    public TileInfo(int x, int z, Identifier id) {
        this.x = x;
        this.z = z;
        this.id = id;
    }
}
