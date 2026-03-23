package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.util.RecipeMatcher;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record PotRecipe(int time, int stirFryCount, Ingredient carrier,
                        NonNullList<Ingredient> ingredients, ItemStackTemplate result) implements BaseRecipe<SimpleInput> {
    public PotRecipe(int time, int stirFryCount, Ingredient carrier,
                     List<Ingredient> ingredients, ItemStackTemplate result) {
        this(time, stirFryCount, carrier, createNonNullList(ingredients), result);
    }

    private static NonNullList<Ingredient> createNonNullList(List<Ingredient> ingredients) {
        // 直接转换，不进行任何验证
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(ingredients);
        return list;
    }



    @Override
    public boolean matches(SimpleInput simpleInput, @NonNull Level level) {

        // 只考虑非空的输入槽位
        List<ItemStack> nonEmptyInputs = simpleInput.getInputs().stream()
                .filter(stack -> !stack.isEmpty())
                .toList();

        // 创建临时的Ingredient列表进行匹配（过滤掉已知的占位符）
        List<Ingredient> recipeIngredients = this.ingredients.stream()
                .filter(ing -> !isKnownPlaceholder(ing))
                .toList();

        // 数量必须匹配
        if (nonEmptyInputs.size() != recipeIngredients.size()) {
            return false;
        }

        return RecipeMatcher.findMatches(nonEmptyInputs, ingredients) != null;
    }

    @Override
    public @NonNull ItemStack assemble(SimpleInput input) {
        return this.result.item().value().getDefaultInstance().copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "pot";
    }

    private static boolean isKnownPlaceholder(Ingredient ingredient) {
        // 只检查是否是明确的屏障方块，避免触发标签绑定
        // 使用更安全的方式检查
        try {
            // 尝试获取第一个物品，如果失败则认为是标签
            var optionalHolder = ingredient.items().findFirst();
            if (optionalHolder.isPresent()) {
                var holder = optionalHolder.get();
                return holder.value() == Items.AIR;
            }
        } catch (Exception e) {
            // 如果出错，认为是标签，不是占位符
            return false;
        }
        return false;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<SimpleInput>> getSerializer() {
        return ModRecipes.POT_SERIALIZER;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<SimpleInput>> getType() {
        return ModRecipes.POT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), new RecipeBookCategory());
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.result.item().value().getDefaultInstance();
    }

}
