package com.mdns.indigo.registry.util;

import org.slf4j.Logger;

/**
 * Utilidades avanzadas para logging
 */
public class LoggerUtil {
    private static Logger logger;
    private static boolean debugMode = false;

    private LoggerUtil() {} // Clase de utilidad

    /**
     * Inicializa el sistema de logging
     */
    public static void initialize(Logger log) {
        logger = log;
    }

    /**
     * Habilita el modo debug para logging detallado
     */
    public static void enableDebugMode() {
        debugMode = true;
        info("Modo debug activado");
    }

    /**
     * Registra un mensaje informativo
     */
    public static void info(String message, Object... args) {
        if (logger != null) {
            logger.info("[Indigo] " + message, args);
        }
    }

    /**
     * Registra un mensaje de debug (solo en modo debug)
     */
    public static void debug(String message, Object... args) {
        if (logger != null && debugMode) {
            logger.debug("[Indigo] " + message, args);
        }
    }

    /**
     * Registra un mensaje de advertencia
     */
    public static void warn(String message, Object... args) {
        if (logger != null) {
            logger.warn("[Indigo] " + message, args);
        }
    }

    /**
     * Registra un mensaje de error
     */
    public static void error(String message, Object... args) {
        if (logger != null) {
            logger.error("[Indigo] " + message, args);
        }
    }

    /**
     * Registra un error con excepción
     */
    public static void error(String message, Throwable throwable) {
        if (logger != null) {
            logger.error("[Indigo] " + message, throwable);
        }
    }
}