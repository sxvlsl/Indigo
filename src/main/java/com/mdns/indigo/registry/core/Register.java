package com.mdns.indigo.registry.core;

import com.mdns.indigo.registry.interfaces.IRegistryHandler;
import com.mdns.indigo.registry.util.BlockEntityHelper;
import com.mdns.indigo.registry.util.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Sistema central de registro mejorado con soporte para Minecraft 1.20+
 */
public class Register {
    private static final Map<Class<?>, IRegistryHandler<?>> handlers = new HashMap<>();
    private static final Map<String, RegistryCategory> categories = new HashMap<>();
    private static String modId;

    private Register() {}

    /**
     * Inicializa el sistema de registro
     *
     * @param modId ID del mod para namespacing
     */
    public static void initialize(String modId) {
        Register.modId = modId;
        registerDefaultHandlers();
    }

    private static void registerDefaultHandlers() {
        registerHandler(Registries.ITEM, RegistryHelper.createItemHandler());
        registerHandler(Registries.BLOCK, RegistryHelper.createBlockHandler());
        registerHandler(Registries.BLOCK_ENTITY_TYPE, RegistryHelper.createBlockEntityHandler());
    }

    public static <T> void registerHandler(Registry<T> registry, IRegistryHandler<?> handler) {
        handlers.put(registry.getKey().getValue().getClass(), handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> RegistryObject<T> register(String name, T entry, Class<T> registryClass) {
        Identifier id = new Identifier(modId, name);
        RegistryObject<T> registryObject = new RegistryObject<>(id);

        // Búsqueda segura del handler con verificación de tipos
        IRegistryHandler<T> handler = findHandlerForType(registryClass);

        T registered = handler.register(id, entry);
        registryObject.setObject(registered);
        return registryObject;
    }

    @SuppressWarnings("unchecked")
    private static <T> IRegistryHandler<T> findHandlerForType(Class<T> registryClass) {
        for (IRegistryHandler<?> handler : handlers.values()) {
            if (handler.getRegistryType().isAssignableFrom(registryClass)) {
                return (IRegistryHandler<T>) handler;
            }
        }
        throw new IllegalArgumentException("No handler found for type: " + registryClass.getName());
    }

    public static String getModId() {
        return modId;
    }

    public static void setModId(String modId) {
        Register.modId = modId;
    }

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntity(
            String name,
            BiFunction<BlockPos, BlockState, T> factory,
            Block... blocks) {

        BlockEntityType<T> type = BlockEntityHelper.createType(name, factory, blocks);
        Identifier id = new Identifier(modId, name);
        RegistryObject<BlockEntityType<T>> registryObject = new RegistryObject<>(id);
        registryObject.setObject(type);
        return registryObject;
    }}