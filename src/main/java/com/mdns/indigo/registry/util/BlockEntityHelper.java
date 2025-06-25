package com.mdns.indigo.registry.util;

import com.mdns.indigo.registry.core.Register;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;

public class BlockEntityHelper {

    /**
     * Crea un BlockEntityType de manera segura sin dependencias circulares
     */
    public static <T extends BlockEntity> BlockEntityType<T> createType(
            String name,
            BiFunction<BlockPos, BlockState, T> factory,
            Block... blocks) {

        // Crear un tipo "temporal" para inicializar
        BlockEntityType<T> tempType = BlockEntityType.Builder.<T>create(
                factory::apply,
                blocks
        ).build(null);

        // Registrar el tipo real
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Register.getModId(), name),
                tempType
        );
    }
}