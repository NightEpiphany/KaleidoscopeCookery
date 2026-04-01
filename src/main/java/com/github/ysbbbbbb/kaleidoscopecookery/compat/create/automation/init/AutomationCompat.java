package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init;

import com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig;
import net.fabricmc.loader.api.FabricLoader;

public class AutomationCompat {
    public static final String ID = "create";

    public static boolean AUTOMATION_LOADED = false;

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID) && GeneralConfig.CREATE_AUTOMATION_ENABLED.get()) {
            AUTOMATION_LOADED = true;
            KitchenAutomationPlugin.init();
        }
    }
}
