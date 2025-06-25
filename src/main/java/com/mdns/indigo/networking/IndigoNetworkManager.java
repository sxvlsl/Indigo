// =================================================================================
// Clase: IndigoNetworkManager (versión completamente corregida y optimizada)
// =================================================================================
package com.mdns.indigo.networking;

import com.mdns.indigo.networking.packets.ClientPacketHandler;
import com.mdns.indigo.networking.packets.IndigoPacket;
import com.mdns.indigo.networking.packets.ServerPacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.thread.ThreadExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IndigoNetworkManager {
    private static final Map<Identifier, NetworkChannel> CHANNELS = new HashMap<>();

    public static NetworkChannel createChannel(String modId, String channelName) {
        Identifier id = new Identifier(modId, channelName);
        NetworkChannel channel = new NetworkChannel(id);
        CHANNELS.put(id, channel);
        return channel;
    }

    public static class NetworkChannel {
        private final Identifier channelId;
        private final Map<Class<?>, PacketRegistration<?>> packetRegistry = new HashMap<>();

        public NetworkChannel(Identifier channelId) {
            this.channelId = channelId;
        }

        public <T extends IndigoPacket> void registerServerBoundPacket(
                Class<T> packetClass,
                Function<PacketByteBuf, T> deserializer,
                ServerPacketHandler<T> handler
        ) {
            PacketRegistration<T> registration = new PacketRegistration<>(deserializer, handler, null);
            packetRegistry.put(packetClass, registration);

            // Registro corregido con nombres de parámetros únicos
            ServerPlayNetworking.registerGlobalReceiver(channelId,
                    (MinecraftServer server, ServerPlayerEntity player,
                     ServerPlayNetworkHandler networkHandler, PacketByteBuf buf, PacketSender sender) -> {

                        T packet = deserializer.apply(buf);
                        SafeExecutionContext context = new SafeExecutionContext(server);

                        server.execute(() -> handler.handle(packet, player, context));
                    }
            );
        }

        public <T extends IndigoPacket> void registerClientBoundPacket(
                Class<T> packetClass,
                Function<PacketByteBuf, T> deserializer,
                ClientPacketHandler<T> handler
        ) {
            PacketRegistration<T> registration = new PacketRegistration<>(deserializer, null, handler);
            packetRegistry.put(packetClass, registration);

            // Handler para el lado del cliente corregido
            ClientPlayNetworking.registerGlobalReceiver(channelId,
                    (MinecraftClient client, ClientPlayNetworkHandler networkHandler,
                     PacketByteBuf buf, PacketSender sender) -> {

                        T packet = deserializer.apply(buf);
                        SafeExecutionContext context = new SafeExecutionContext(client);

                        client.execute(() -> {
                            // Usar handler personalizado en lugar de networkHandler
                            if (client.player != null) {
                                handler.handle(packet, client.player, context);
                            }
                        });
                    }
            );
        }

        public <T extends IndigoPacket> void sendToClient(ServerPlayerEntity player, T packet) {
            PacketByteBuf buf = PacketByteBufs.create();
            packet.serialize(buf);
            ServerPlayNetworking.send(player, channelId, buf);
        }

        public <T extends IndigoPacket> void sendToServer(T packet) {
            PacketByteBuf buf = PacketByteBufs.create();
            packet.serialize(buf);
            ClientPlayNetworking.send(channelId, buf);
        }
    }

    private record PacketRegistration<T extends IndigoPacket>(
            Function<PacketByteBuf, T> deserializer,
            ServerPacketHandler<T> serverHandler,
            ClientPacketHandler<T> clientHandler
    ) {}
}
