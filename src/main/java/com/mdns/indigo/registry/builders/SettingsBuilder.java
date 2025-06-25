package com.mdns.indigo.registry.builders;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.util.Supplier;

/**
 * Builder fluido actualizado para Minecraft 1.20.1+
 */
public class SettingsBuilder {
    private int maxCount = 64;
    private int maxDamage = 0;
    private RegistryKey<net.minecraft.registry.Registry<Item>> groupKey;
    private Rarity rarity = Rarity.COMMON;
    private boolean fireproof = false;
    private Supplier<TagKey<Item>> recipeRemainder;

    public SettingsBuilder maxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public SettingsBuilder maxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public SettingsBuilder group(RegistryKey<net.minecraft.registry.Registry<Item>> groupKey) {
        this.groupKey = groupKey;
        return this;
    }

    public SettingsBuilder rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public SettingsBuilder fireproof() {
        this.fireproof = true;
        return this;
    }

    public SettingsBuilder recipeRemainder(Supplier<TagKey<Item>> recipeRemainder) {
        this.recipeRemainder = recipeRemainder;
        return this;
    }

    public Item.Settings build() {
        Item.Settings settings = new Item.Settings()
                .maxCount(maxCount)
                .rarity(rarity);

        if (maxDamage > 0) {
            settings.maxDamage(maxDamage);
        }

        if (fireproof) {
            settings.fireproof();
        }

        if (recipeRemainder != null) {
            // Implementación de recipe remainder
        }

        // En versiones modernas, los grupos se manejan diferente
        // La asignación a grupo creativo se hace durante el registro del item
        return settings;
    }
}