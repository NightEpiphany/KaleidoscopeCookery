package com.github.ysbbbbbb.kaleidoscopecookery.crafting.teafluid;

import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.teafluid.ITeaFluid;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.Map;

public class TeaFluidManager {
    private static final Map<ResourceLocation, ITeaFluid> ALL_TEA_FLUIDS = Maps.newLinkedHashMap();
    private static final Map<ResourceLocation, Fluid> BOUND_FLUID_TYPES = Maps.newLinkedHashMap();

    public static void registerTeaFluid(ITeaFluid teaFluid) {
        if (ALL_TEA_FLUIDS.containsKey(teaFluid.name())) {
            throw new IllegalArgumentException("Tea fluid with name " + teaFluid.name() + " already exists!");
        }
        ALL_TEA_FLUIDS.put(teaFluid.name(), teaFluid);
    }

    public static void bindFluid(ResourceLocation name, Fluid fluidType) {
        if (BOUND_FLUID_TYPES.containsKey(name)) {
            throw new IllegalArgumentException("Tea fluid with name " + name + " is already bound to another fluid!");
        }
        BOUND_FLUID_TYPES.put(name, fluidType);
    }

    public static ITeaFluid getTeaFluid(ResourceLocation name) { return ALL_TEA_FLUIDS.get(name); }

    @Nullable
    public static Fluid getBoundFluid(ResourceLocation name) { return BOUND_FLUID_TYPES.getOrDefault(name, null); }

    public static boolean contains(ResourceLocation name) { return ALL_TEA_FLUIDS.containsKey(name); }

    public static Map<ResourceLocation, ITeaFluid> getAllTeaFluids() { return ALL_TEA_FLUIDS; }

    public static Map<ResourceLocation, Fluid> getBoundFluidTypes() { return BOUND_FLUID_TYPES; }
}
