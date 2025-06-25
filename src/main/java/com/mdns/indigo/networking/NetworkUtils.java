// =================================================================================
// Clase: NetworkUtils
// Responsabilidad: Utilidades avanzadas de serialización
// =================================================================================
package com.mdns.indigo.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class NetworkUtils {
    // Serialización de tipos complejos
    public static void writeVec3d(PacketByteBuf buf, Vec3d vec) {
        buf.writeDouble(vec.getX());
        buf.writeDouble(vec.getY());
        buf.writeDouble(vec.getZ());
    }

    public static Vec3d readVec3d(PacketByteBuf buf) {
        return new Vec3d(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble()
        );
    }

    public static void writeBlockPos(PacketByteBuf buf, BlockPos pos) {
        buf.writeBlockPos(pos);
    }

    public static BlockPos readBlockPos(PacketByteBuf buf) {
        return buf.readBlockPos();
    }

    // Serialización de UUIDs
    public static void writeUUID(PacketByteBuf buf, java.util.UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    public static java.util.UUID readUUID(PacketByteBuf buf) {
        return new java.util.UUID(buf.readLong(), buf.readLong());
    }

    // Serialización de NBT
    public static void writeNbt(PacketByteBuf buf, net.minecraft.nbt.NbtCompound compound) {
        buf.writeNbt(compound);
    }

    public static net.minecraft.nbt.NbtCompound readNbt(PacketByteBuf buf) {
        return buf.readNbt();
    }

    // Método para tipos genéricos
    public static <T extends Enum<T>> T readEnum(Class<T> enumClass, PacketByteBuf buf) {
        return enumClass.getEnumConstants()[buf.readVarInt()];
    }

    public static void writeEnum(Enum<?> value, PacketByteBuf buf) {
        buf.writeVarInt(value.ordinal());
    }
}