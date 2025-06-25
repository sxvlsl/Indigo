package com.mdns.indigo.registry.builders;

import com.mdns.indigo.registry.core.Register;
import com.mdns.indigo.registry.interfaces.BlockEntityTypeAware;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

import java.util.function.BiFunction;

/**
 * Builder especializado para BlockEntityType con solución a la dependencia circular
 */
public class BlockEntityBuilder<T extends BlockEntity> {
    private final String name;
    private final BiFunction<BlockPos, BlockState, T> factory;
    private final Block[] blocks;
    private boolean registerRenderer = false;

    // Referencia al tipo para resolver la dependencia circular
    private BlockEntityType<T> typeInstance;

    public BlockEntityBuilder(String name, BiFunction<BlockPos, BlockState, T> factory, Block... blocks) {
        if (blocks == null || blocks.length == 0) {
            throw new IllegalArgumentException("At least one block must be provided");
        }

        this.name = name;
        this.factory = factory;
        this.blocks = blocks;
    }

    public BlockEntityBuilder<T> withRenderer() {
        this.registerRenderer = true;
        return this;
    }

    public BlockEntityType<T> register() {
        // Crear el BlockEntityType usando un método seguro
        typeInstance = createBlockEntityType();

        // Registrar usando el sistema seguro de tipos
        Register.register(name, typeInstance, BlockEntityType.class);

        // Registrar el renderer si es necesario
        if (registerRenderer) {
            registerBlockEntityRenderer();
        }

        return typeInstance;
    }

    private BlockEntityType<T> createBlockEntityType() {
        return BlockEntityType.Builder.<T>create(
                (pos, state) -> {
                    T entity = factory.apply(pos, state);

                    // Inyectar el tipo si es necesario
                    if (entity instanceof BlockEntityTypeAware) {
                        ((BlockEntityTypeAware) entity).setBlockEntityType(typeInstance);
                    }

                    return entity;
                },
                blocks
        ).build(null);
    }

    private void registerBlockEntityRenderer() {
        // Implementación de registro de renderer
    }
}