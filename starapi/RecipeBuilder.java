package net.glitch.starapi;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class RecipeBuilder {

    private final JavaPlugin plugin;
    private final ShapedRecipe recipe;
    private final Map<Character, RecipeIngredient> ingredients;

    public RecipeBuilder(JavaPlugin plugin, ItemStack result, String key) {
        NamespacedKey recipeKey = new NamespacedKey(plugin, key);
        this.recipe = new ShapedRecipe(recipeKey, result);
        this.ingredients = new HashMap<>();
        this.plugin = plugin;
    }

    public RecipeBuilder setShape(String... shape) {
        recipe.shape(shape);
        return this;
    }

    public RecipeBuilder setIngredient(char key, Material material, int amount) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(material, amount);
        recipe.setIngredient(key, recipeIngredient.getMaterialData());
        ingredients.put(key, recipeIngredient);
        return this;
    }

    public RecipeBuilder setIngredient(char key, ItemStack itemStack, int amount) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(itemStack, amount);
        recipe.setIngredient(key, recipeIngredient.getMaterialData());
        ingredients.put(key, recipeIngredient);
        return this;
    }

    public ShapedRecipe build() {
        for (Map.Entry<Character, RecipeIngredient> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue().getMaterialData());
        }
        return recipe;
    }

    private static class RecipeIngredient {
        private final ItemStack itemStack;

        public RecipeIngredient(Material material, int amount) {
            this.itemStack = new ItemStack(material, amount);
        }

        public RecipeIngredient(ItemStack itemStack, int amount) {
            this.itemStack = itemStack.clone();
            this.itemStack.setAmount(amount);
        }

        public MaterialData getMaterialData() {
            return itemStack.getData();
        }

        public int getAmount() {
            return itemStack.getAmount();
        }

        public boolean isSameItem(ItemStack other) {
            return itemStack.isSimilar(other);
        }
    }
}
