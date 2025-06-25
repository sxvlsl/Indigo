package com.mdns.indigo.networking.packets;

import net.minecraft.network.PacketByteBuf;

public interface IndigoPacket {
    void serialize(PacketByteBuf buf);
    default int getDiscriminator() {
        return this.getClass().getName().hashCode();
    }
}