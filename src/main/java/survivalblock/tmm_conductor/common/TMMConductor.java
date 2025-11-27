package survivalblock.tmm_conductor.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.tmm_conductor.common.init.TMMConductorBlockEntities;
import survivalblock.tmm_conductor.common.init.TMMConductorBlocks;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

public class TMMConductor implements ModInitializer {
	public static final String MOD_ID = "tmm_conductor";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final boolean DEVELOPMENT = FabricLoader.getInstance().isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
        TMMConductorRoles.init();
        TMMConductorBlocks.init();
        TMMConductorBlockEntities.init();
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}