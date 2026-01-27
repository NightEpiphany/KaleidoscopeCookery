package com.github.ysbbbbbb.kaleidoscopecookery.mixin.compact;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// feat:当不加载乐事兼容时禁止数据文件的无用加载
@Mixin(ShapedRecipe.class)
public abstract class RecipeParserMixin {

    @Unique
    private static final String FARMERS_DELIGHT = "farmersdelight";
    @Unique
    private static final String RUSTIC_DELIGHT = "rusticdelight";
    @Unique
    private static final String TRIALANDTALES_DELIGHT = "trailandtales_delight";
    @Unique
    private static final String BREWIN_AND_CHEWIN = "brewinandchewin";


    @Inject(method = "itemFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/DefaultedRegistry;getOptional(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;", shift = At.Shift.BEFORE), cancellable = true)
    private static void itemFromJson(JsonObject itemObject, CallbackInfoReturnable<Item> cir, @Local String s) {
        if (!FabricLoader.getInstance().isModLoaded(FARMERS_DELIGHT)) {
            if (s.contains(FARMERS_DELIGHT) || s.contains(RUSTIC_DELIGHT) || s.contains(TRIALANDTALES_DELIGHT) || s.contains(BREWIN_AND_CHEWIN)) cir.setReturnValue(Items.BARRIER);
        }else {
            if (!FabricLoader.getInstance().isModLoaded(RUSTIC_DELIGHT)) {
                if (s.contains(RUSTIC_DELIGHT)) cir.setReturnValue(Items.BARRIER);
            }
            if (!FabricLoader.getInstance().isModLoaded(TRIALANDTALES_DELIGHT)) {
                if (s.contains(TRIALANDTALES_DELIGHT)) cir.setReturnValue(Items.BARRIER);
            }
            if (!FabricLoader.getInstance().isModLoaded(BREWIN_AND_CHEWIN)) {
                if (s.contains(BREWIN_AND_CHEWIN)) cir.setReturnValue(Items.BARRIER);
            }
        }
    }

}
