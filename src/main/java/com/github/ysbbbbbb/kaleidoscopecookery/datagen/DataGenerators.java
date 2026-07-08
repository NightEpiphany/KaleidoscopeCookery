package com.github.ysbbbbbb.kaleidoscopecookery.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();


        pack.addProvider(AdvancementGenerator::new);
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(SoundDefinitionsGenerator::new);
    }
}
