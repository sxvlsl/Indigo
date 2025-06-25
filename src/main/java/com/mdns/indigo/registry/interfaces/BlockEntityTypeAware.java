package com.mdns.indigo.registry.interfaces;

import net.minecraft.block.entity.BlockEntityType;

/**
 * Interfaz para BlockEntities que necesitan conocer su tipo registrado
 */
public interface BlockEntityTypeAware {
    void setBlockEntityType(BlockEntityType<?> type);
}