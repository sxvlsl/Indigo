package com.mdns.indigo.registry.builders;

import com.mdns.indigo.registry.core.Register;
import com.mdns.indigo.registry.util.ModelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

/**
 * Builder de items actualizado para Minecraft 1.20.1+
 */
public class ItemBuilder {
    private final String name;
    private final Item item;
    private RegistryKey<net.minecraft.registry.Registry<Item>> creativeTab;
    private boolean registerModel = true;

    public ItemBuilder(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public ItemBuilder creativeTab(RegistryKey<Registry<Item>> creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    public ItemBuilder noModel() {
        this.registerModel = false;
        return this;
    }

    public Item register() {
        Identifier id = new Identifier(Register.getModId(), name);

        // Registrar el item
        Registry.register(Registries.ITEM, id, item);

        // Registrar en grupo creativo si está especificado
        if (creativeTab != null) {
            // Implementación de registro en grupo creativo
            // (Normalmente se haría con un sistema de callbacks)
        }

        // Registrar modelo automáticamente
        if (registerModel) {
            ModelRegistry.registerItemModel(id);
        }

        return item;
    }
}