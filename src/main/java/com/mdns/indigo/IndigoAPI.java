package com.mdns.indigo;

import com.mdns.indigo.registry.core.Register;
import com.mdns.indigo.registry.util.LoggerUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Punto de entrada principal de la API Indigo para Minecraft Fabric 1.20.1+
 *
 * <p>Esta clase inicializa todos los sistemas principales de la API y proporciona
 * acceso a sus funcionalidades para mods que dependan de esta API.</p>
 */
public class IndigoAPI implements ModInitializer {
	// Logger de la API para registro de eventos
	public static final Logger LOGGER = LoggerFactory.getLogger("IndigoAPI");

	// ID del mod y versión de la API
	public static final String MOD_ID = "indigo";
	public static final String API_VERSION = "1.0.0";

	// Instancia única de la API
	private static IndigoAPI instance;

	// Estado de inicialización
	private boolean initialized = false;

	@Override
	public void onInitialize() {
		// Garantizar una sola instancia
		if (instance != null) {
			LOGGER.warn("IndigoAPI ya ha sido inicializada anteriormente");
			return;
		}

		instance = this;
		initializeAPI();

		// Registrar evento de inicio de servidor
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			LOGGER.info("IndigoAPI está activa en el servidor");
		});
	}

	/**
	 * Inicializa todos los sistemas principales de la API
	 */
	private void initializeAPI() {
		if (initialized) {
			return;
		}

		long startTime = System.currentTimeMillis();

		// 1. Inicializar sistema de logging mejorado
		LoggerUtil.initialize(LOGGER);

		// 2. Inicializar sistema de registro central
		Register.initialize(MOD_ID);

		// 3. Registrar componentes básicos de la API
		registerAPIContent();

		// 4. Inicializar otros sistemas (eventos, configuración, etc.)
		// (Se añadirán en futuras implementaciones)

		initialized = true;
		long duration = System.currentTimeMillis() - startTime;

		LOGGER.info("IndigoAPI v{} inicializada correctamente ({} ms)", API_VERSION, duration);
	}

	/**
	 * Registra los componentes básicos de la API
	 */
	private void registerAPIContent() {
		// Aquí se registrarían los objetos propios de la API
		// Por ahora es un placeholder para futuras implementaciones

		LOGGER.debug("Componentes básicos de la API registrados");
	}

	/**
	 * @return La instancia activa de la API
	 * @throws IllegalStateException Si la API no ha sido inicializada
	 */
	public static IndigoAPI getInstance() {
		if (instance == null) {
			throw new IllegalStateException("IndigoAPI no ha sido inicializada");
		}
		return instance;
	}

	/**
	 * @return El identificador de la API en formato namespace:path
	 */
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	/**
	 * @return El logger de la API para registro de eventos
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * @return true si la API ha sido completamente inicializada
	 */
	public static boolean isInitialized() {
		return instance != null && instance.initialized;
	}
}