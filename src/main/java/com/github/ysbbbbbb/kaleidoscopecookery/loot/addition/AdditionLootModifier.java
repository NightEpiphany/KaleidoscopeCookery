package com.github.ysbbbbbb.kaleidoscopecookery.loot.addition;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class AdditionLootModifier extends LootModifier {
    public static final Supplier<Codec<AdditionLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(instance -> codecStart(instance).and(instance.group(
                    ResourceLocation.CODEC.fieldOf("loot_table_type").forGetter(m -> m.lootTableType),
                    ResourceLocation.CODEC.optionalFieldOf("loot_table_id").forGetter(m -> Optional.ofNullable(m.lootTableId)),
                    ResourceLocation.CODEC.fieldOf("loot_table_add").forGetter(m -> m.lootTableAdd)
            )).apply(instance, AdditionLootModifier::new)));

    private final ResourceLocation lootTableType;
    private final @Nullable ResourceLocation lootTableId;
    private final ResourceLocation lootTableAdd;

    public AdditionLootModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTableType,
                                Optional<ResourceLocation> lootTableId, ResourceLocation lootTableAdd) {
        super(conditionsIn);
        this.lootTableType = lootTableType;
        this.lootTableId = lootTableId.orElse(null);
        this.lootTableAdd = lootTableAdd;
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ResourceLocation currentLootTable = context.getQueriedLootTableId();
        if (!currentLootTable.equals(lootTableAdd) && typeAreEquals(context) && idAreEquals(context)) {
            LootTable additionTable = context.getResolver().getLootTable(lootTableAdd);
            //additionTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        }
        return generatedLoot;
    }

    private boolean typeAreEquals(LootContext context) {
        ResourceLocation currentLootTable = context.getQueriedLootTableId();
        LootTable lootTable = context.getResolver().getLootTable(currentLootTable);
        return Objects.equals(lootTable.getParamSet(), LootContextParamSets.get(lootTableType));
    }

    private boolean idAreEquals(LootContext context) {
        if (this.lootTableId == null) {
            return true;
        }
        return context.getQueriedLootTableId().equals(this.lootTableId);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
