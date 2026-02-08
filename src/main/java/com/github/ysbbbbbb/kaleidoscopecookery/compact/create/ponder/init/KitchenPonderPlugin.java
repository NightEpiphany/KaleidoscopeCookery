package com.github.ysbbbbbb.kaleidoscopecookery.compact.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.zurrtum.create.client.ponder.api.registration.PonderPlugin;
import com.zurrtum.create.client.ponder.api.registration.PonderSceneRegistrationHelper;
import com.zurrtum.create.client.ponder.api.registration.PonderTagRegistrationHelper;
import com.zurrtum.create.client.ponder.foundation.PonderIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

public class KitchenPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return KaleidoscopeCookery.MOD_ID;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerScenes(PonderSceneRegistrationHelper<Identifier> helper) {
        KitchenBlockPonderScreen.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<Identifier> helper) {
        KitchenBlockPonderTag.register(helper);
    }

    public static void init() {
        PonderIndex.addPlugin(new KitchenPonderPlugin());
    }
}
