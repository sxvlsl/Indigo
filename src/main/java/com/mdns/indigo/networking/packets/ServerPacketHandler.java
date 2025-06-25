package com.mdns.indigo.networking.packets;

import com.mdns.indigo.networking.SafeExecutionContext;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface ServerPacketHandler<T extends IndigoPacket> {
    void handle(T packet, ServerPlayerEntity player, SafeExecutionContext context);
}

