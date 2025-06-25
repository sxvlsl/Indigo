package com.mdns.indigo.registry.util;

import com.mdns.indigo.registry.core.Register;
import net.minecraft.util.Identifier;

/**
 * Utilidades para trabajar con recursos y identificadores
 */
public class ResourceUtil {
    public static Identifier prefixPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + "/" + identifier.getPath());
    }

    public static Identifier suffixPath(Identifier identifier, String suffix) {
        return new Identifier(identifier.getNamespace(), identifier.getPath() + suffix);
    }

    public static Identifier modId(String path) {
        return new Identifier(Register.getModId(), path);
    }
}