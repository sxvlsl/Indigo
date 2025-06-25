package com.mdns.indigo.registry.interfaces;

import net.minecraft.util.Identifier;

/**
 * Interface para manejar diferentes tipos de registros
 */
public interface IRegistryHandler<T> {
    /**
     * Registra un objeto en el sistema de Minecraft
     *
     * @param identifier Identificador único del objeto
     * @param entry Objeto a registrar
     * @return El objeto registrado
     */
    T register(Identifier identifier, T entry);

    /**
     * @return El tipo de registro que maneja este handler
     */
    Class<? super T> getRegistryType();
}