package com.mdns.indigo.registry.core;

import com.mdns.indigo.registry.interfaces.IRegistryEntry;
import net.minecraft.util.Identifier;

/**
 * Contenedor para objetos registrados que permite acceso seguro y gestión
 *
 * @param <T> Tipo del objeto contenido
 */
public class RegistryObject<T> implements IRegistryEntry {
    private final Identifier id;
    private T object;
    private boolean isRegistered = false;

    public RegistryObject(Identifier id) {
        this.id = id;
    }

    /**
     * Establece el objeto contenido después del registro
     *
     * @param object Objeto registrado
     * @throws IllegalStateException Si el objeto ya ha sido registrado
     */
    protected void setObject(T object) {
        if (isRegistered) {
            throw new IllegalStateException("Cannot modify a registered RegistryObject");
        }
        this.object = object;
        this.isRegistered = true;
    }

    /**
     * @return El objeto contenido
     * @throws IllegalStateException Si el objeto aún no ha sido registrado
     */
    public T get() {
        if (!isRegistered) {
            throw new IllegalStateException("RegistryObject not yet registered: " + id);
        }
        return object;
    }

    @Override
    public String getRegistryName() {
        return id.getPath();
    }

    @Override
    public void register() {
        // La implementación real de registro ocurre en Registrar
    }

    public Identifier getId() {
        return id;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}