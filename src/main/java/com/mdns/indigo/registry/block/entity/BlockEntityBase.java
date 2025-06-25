package com.mdns.indigo.registry.block.entity;

import com.mdns.indigo.registry.interfaces.BlockEntityTypeAware;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

/**
 * Implementación base para BlockEntities con soporte de tipo
 */
public abstract class BlockEntityBase extends BlockEntity implements BlockEntityTypeAware {
    protected BlockEntityType<?> blockEntityType;

    public BlockEntityBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.blockEntityType = type;
    }

    @Override
    public void setBlockEntityType(BlockEntityType<?> type) {
        this.blockEntityType = type;
    }
}