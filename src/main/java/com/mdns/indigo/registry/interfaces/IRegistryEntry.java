package com.mdns.indigo.registry.interfaces;

/**
 * Interface base para todas las entradas de registro.
 * Proporciona métodos esenciales para la identificación y gestión de objetos registrados.
 */
public interface IRegistryEntry {
    /**
     * @return El identificador único de este objeto en el registro
     */
    String getRegistryName();

    /**
     * @return El path del modelo para este objeto
     */
    default String getModelPath() {
        return "item/" + getRegistryName();
    }

    /**
     * Método llamado durante la fase de registro
     */
    void register();
}