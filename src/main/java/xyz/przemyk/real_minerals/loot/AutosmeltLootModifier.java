package xyz.przemyk.real_minerals.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AutosmeltLootModifier extends LootModifier {

    public AutosmeltLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ArrayList<ItemStack> smelted = new ArrayList<>();
        for (ItemStack itemStack : generatedLoot) {
            smelted.add(context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemStack), context.getLevel())
                    .map(SmeltingRecipe::getResultItem)
                    .filter(smeltedStack -> ! smeltedStack.isEmpty())
                    .map(smeltedStack -> ItemHandlerHelper.copyStackWithSize(smeltedStack, itemStack.getCount() * smeltedStack.getCount()))
                    .orElse(itemStack));
        }
        return smelted;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AutosmeltLootModifier> {

        @Override
        public AutosmeltLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new AutosmeltLootModifier(conditions);
        }

        @Override
        public JsonObject write(AutosmeltLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
