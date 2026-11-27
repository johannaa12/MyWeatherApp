package com.example.myweatherapp

import com.example.myweatherapp.recipe.Flavor
import com.example.myweatherapp.recipe.Recipe
import com.example.myweatherapp.recipe.RecipeListItem
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeTest {

    @Test
    fun testRecipeCreation() {
        val recipe = Recipe("Chocolate Cake", "Delicious chocolate cake recipe", Flavor.SWEET)
        assertEquals("Chocolate Cake", recipe.title)
        assertEquals("Delicious chocolate cake recipe", recipe.description)
        assertEquals(Flavor.SWEET, recipe.flavor)
        assertEquals("Sweet", recipe.flavor.displayName)
    }

    @Test
    fun testRecipeListItemTypes() {
        val header = RecipeListItem.FlavorHeader(Flavor.SAVORY)
        val recipeItem = RecipeListItem.RecipeItem(
            Recipe("Garlic Bread", "Crispy garlic bread", Flavor.SAVORY)
        )

        assertEquals(Flavor.SAVORY, header.flavor)
        assertEquals("Garlic Bread", recipeItem.recipe.title)
    }
}
