package hunternif.mc.impl.atlas.network.packet.c2s;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.packet.CustomPayload;

public interface C2SPacket extends CustomPayload {
	default void send() {
		NetworkManager.sendToServer(this);
	}
}
