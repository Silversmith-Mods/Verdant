package com.ordana.verdant.configs;

import com.ordana.verdant.Verdant;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;
import net.mehvahdjukaar.moonlight.api.platform.configs.ModConfigHolder;

import java.util.List;
import java.util.function.Supplier;

public class CommonConfigs {


    public static final ModConfigHolder SERVER_SPEC;

    public static final Supplier<Boolean> CREATIVE_TAB;
    public static final Supplier<Boolean> CREATIVE_DROP;
    public static final Supplier<Boolean> DEBUG_RESOURCES;
    public static final Supplier<String> GENERIC_BARK;
    public static final Supplier<List<String>> LEAF_PILES_BLACKLIST;


    public static void init() {
    }

    static{
        ConfigBuilder builder = ConfigBuilder.create(Verdant.res("common"), ConfigType.COMMON);

        builder.push("general");
        CREATIVE_TAB = builder.define("creative_tab", true);
        CREATIVE_DROP = builder.comment("Drop stuff when in creative").define("drop_in_creative", false);
        DEBUG_RESOURCES = builder.comment("Save generated resources to disk in a 'debug' folder in your game directory. Mainly for debug purposes but can be used to generate assets in all wood types for your mods :0")
                .define("debug_save_dynamic_pack", false);
        GENERIC_BARK = builder.define("generic_bark", "");
        LEAF_PILES_BLACKLIST = builder.comment("leaves that wont spawn leaf piles").define("leaf_piles_blacklist", List.of());

        builder.pop();

        SERVER_SPEC = builder.build();
        SERVER_SPEC.forceLoad();
    }
}
