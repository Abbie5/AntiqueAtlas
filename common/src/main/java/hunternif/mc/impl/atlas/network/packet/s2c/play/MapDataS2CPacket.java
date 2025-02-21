package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.client.gui.GuiAtlas;
import hunternif.mc.impl.atlas.core.AtlasData;
import hunternif.mc.impl.atlas.network.packet.s2c.S2CPacket;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

/**
 * Used to sync atlas data from server to client.
 *
 * @author Hunternif
 * @author Haven King
 */
public record MapDataS2CPacket(
        int atlasID,
        NbtCompound data
) implements S2CPacket {
    public static final Id<MapDataS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "map", "data"));
    public static final PacketCodec<ByteBuf, MapDataS2CPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, MapDataS2CPacket::atlasID,
            PacketCodecs.NBT_COMPOUND, MapDataS2CPacket::data,
            MapDataS2CPacket::new
    );

    @Override
    public Id<?> getId() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    public static void apply(MapDataS2CPacket packet, NetworkManager.PacketContext context) {
        if (packet.data == null) return;

        context.queue(() -> {
            AtlasData atlasData = AntiqueAtlasMod.tileData.getData(packet.atlasID, context.getPlayer().getEntityWorld());
            atlasData.updateFromNbt(packet.data);

            if (AntiqueAtlasMod.CONFIG.doSaveBrowsingPos && MinecraftClient.getInstance().currentScreen instanceof GuiAtlas) {
                ((GuiAtlas) MinecraftClient.getInstance().currentScreen).loadSavedBrowsingPosition();
            }
        });
    }
}
