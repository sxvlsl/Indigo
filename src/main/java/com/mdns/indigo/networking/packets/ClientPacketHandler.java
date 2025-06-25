package com.mdns.indigo.networking.packets;

import com.mdns.indigo.networking.SafeExecutionContext;
import net.minecraft.entity.player.PlayerEntity;

@FunctionalInterface
public interface ClientPacketHandler<T extends IndigoPacket> {
    void handle(T packet, PlayerEntity player, SafeExecutionContext context);
}
