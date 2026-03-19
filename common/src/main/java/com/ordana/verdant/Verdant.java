package com.ordana.verdant;

import com.ordana.verdant.configs.ClientConfigs;
import com.ordana.verdant.configs.CommonConfigs;
import com.ordana.verdant.dynamicpack.ServerDynamicResourcesHandler;
import com.ordana.verdant.network.NetworkHandler;
import com.ordana.verdant.reg.*;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Verdant {

    public static final String MOD_ID = "verdant";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation res(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void commonInit() {

        CommonConfigs.init();

        if (PlatHelper.getPhysicalSide().isClient()) {
            ClientConfigs.init();
            VerdantClient.init();
        }

        PlatHelper.addCommonSetup(Verdant::setup);

        RegHelper.registerDynamicResourceProvider(ServerDynamicResourcesHandler.INSTANCE);

        ModCreativeTab.init();

        NetworkHandler.init();

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModParticles.init();
        ModWorldgen.init();
    }

    public static void setup() {
        ModCompostable.register();
    }
}
