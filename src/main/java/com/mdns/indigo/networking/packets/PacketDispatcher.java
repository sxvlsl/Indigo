// =================================================================================
// Clase: PacketDispatcher
// Responsabilidad: Envío optimizado de paquetes con prioridades
// =================================================================================
package com.mdns.indigo.networking.packets;

import com.mdns.indigo.networking.IndigoNetworkManager.NetworkChannel;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketDispatcher {
    public enum Priority {
        IMMEDIATE,  // Envío inmediato (para inputs críticos)
        NORMAL,     // Envío en el próximo tick (para la mayoría de actualizaciones)
        DEFERRED    // Envío agrupado (para actualizaciones no críticas)
    }

    private static final ConcurrentLinkedQueue<PacketTask> PACKET_QUEUE = new ConcurrentLinkedQueue<>();

    public static <T extends IndigoPacket> void dispatchToPlayer(
            T packet,
            ServerPlayerEntity player,
            Priority priority,
            NetworkChannel channel
    ) {
        switch (priority) {
            case IMMEDIATE:
                channel.sendToClient(player, packet);
                break;
            case NORMAL:
                PACKET_QUEUE.add(new PacketTask(packet, player, channel));
                break;
            case DEFERRED:
                scheduleDeferredDispatch(packet, player, channel);
                break;
        }
    }

    public static void processQueue() {
        PacketTask task;
        while ((task = PACKET_QUEUE.poll()) != null) {
            task.channel().sendToClient(task.player(), task.packet());
        }
    }

    private static <T extends IndigoPacket> void scheduleDeferredDispatch(
            T packet,
            ServerPlayerEntity player,
            NetworkChannel channel
    ) {
        // Implementación real usaría un sistema de batching con temporizador
        PACKET_QUEUE.add(new PacketTask(packet, player, channel));
    }

    private record PacketTask(
            IndigoPacket packet,
            ServerPlayerEntity player,
            NetworkChannel channel
    ) {}
}
