package net.glitchtechs.starapi

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.material.MaterialData
import org.bukkit.plugin.java.JavaPlugin


class RecipeBuilder(plugin: JavaPlugin, result: ItemStack?, key: String?) {
    private val plugin: JavaPlugin
    private val recipe: ShapedRecipe
    private val ingredients: MutableMap<Char?, RecipeIngredient?>

    init {
        val recipeKey = NamespacedKey(plugin, key!!)
        this.recipe = ShapedRecipe(recipeKey, result!!)
        this.ingredients = HashMap()
        this.plugin = plugin
    }

    fun setShape(vararg shape: String?): RecipeBuilder {
        recipe.shape(*shape)
        return this
    }

    fun setIngredient(key: Char, material: Material?, amount: Int): RecipeBuilder {
        val recipeIngredient = RecipeIngredient(material, amount)
        recipe.setIngredient(key, recipeIngredient.materialData!!)
        ingredients[key] = recipeIngredient
        return this
    }

    fun setIngredient(key: Char, itemStack: ItemStack, amount: Int): RecipeBuilder {
        val recipeIngredient = RecipeIngredient(itemStack, amount)
        recipe.setIngredient(key, recipeIngredient.materialData!!)
        ingredients[key] = recipeIngredient
        return this
    }

    fun build(): ShapedRecipe {
        val var1: Iterator<*> = ingredients.entries.iterator()

        while (var1.hasNext()) {
            val entry: Map.Entry<Char, RecipeIngredient> = var1.next() as Map.Entry<Char, RecipeIngredient>
            recipe.setIngredient(entry.key, entry.value.materialData!!)
        }

        return this.recipe
    }

    private class RecipeIngredient {
        private val itemStack: ItemStack

        constructor(material: Material?, amount: Int) {
            this.itemStack = ItemStack(material!!, amount)
        }

        constructor(itemStack: ItemStack, amount: Int) {
            this.itemStack = itemStack.clone()
            this.itemStack.amount = amount
        }

        val materialData: MaterialData?
            get() = itemStack.data

        val amount: Int
            get() = itemStack.amount

        fun isSameItem(other: ItemStack?): Boolean {
            return itemStack.isSimilar(other)
        }
    }
}