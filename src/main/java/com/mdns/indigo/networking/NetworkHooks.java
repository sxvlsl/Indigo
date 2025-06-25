package com.mdns.indigo.networking;

import com.mdns.indigo.networking.packets.PacketDispatcher;
import net.minecraft.server.MinecraftServer;

public class NetworkHooks {
    public static void onServerTick(MinecraftServer server) {
        PacketDispatcher.processQueue();
    }
}