package com.mdns.indigo.registry.util;

import com.mdns.indigo.registry.interfaces.IRegistryHandler;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unchecked")
public class RegistryHelper {

    public static IRegistryHandler<Item> createItemHandler() {
        return new IRegistryHandler<Item>() {
            @Override
            public Item register(Identifier identifier, Item entry) {
                return Registry.register(Registries.ITEM, identifier, entry);
            }

            @Override
            public Class<? super Item> getRegistryType() {
                return Item.class;
            }
        };
    }

    public static IRegistryHandler<Block> createBlockHandler() {
        return new IRegistryHandler<Block>() {
            @Override
            public Block register(Identifier identifier, Block entry) {
                return Registry.register(Registries.BLOCK, identifier, entry);
            }

            @Override
            public Class<? super Block> getRegistryType() {
                return Block.class;
            }
        };
    }

    public static IRegistryHandler<BlockEntityType<?>> createBlockEntityHandler() {
        return new IRegistryHandler<BlockEntityType<?>>() {
            @Override
            public BlockEntityType<?> register(Identifier identifier, BlockEntityType<?> entry) {
                return Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, entry);
            }

            @Override
            @SuppressWarnings("unchecked")
            public Class<? super BlockEntityType<?>> getRegistryType() {
                // Solución segura para el tipo genérico
                return (Class<? super BlockEntityType<?>>) (Class<?>) BlockEntityType.class;
            }
        };
    }
}