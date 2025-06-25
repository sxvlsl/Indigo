package com.mdns.indigo.registry.core;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Categoría para organizar elementos registrados
 */
public class RegistryCategory {
    private final String name;
    private final List<RegistryObject<?>> entries = new ArrayList<>();

    public RegistryCategory(String name) {
        this.name = name;
    }

    /**
     * Registra un elemento en esta categoría
     */
    public <T> RegistryObject<T> register(String name, T entry, Class<T> registryClass) {
        Identifier id = new Identifier(Register.getModId(), this.name + "/" + name);
        RegistryObject<T> registryObject = new RegistryObject<>(id);

        // Registro real (implementación simplificada)
        // En una implementación real usaríamos Registrar.register()
        registryObject.setObject(entry);
        entries.add(registryObject);

        return registryObject;
    }

    public String getName() {
        return name;
    }

    public List<RegistryObject<?>> getEntries() {
        return entries;
    }
}