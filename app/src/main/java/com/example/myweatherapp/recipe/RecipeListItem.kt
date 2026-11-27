package com.example.myweatherapp.recipe

sealed class RecipeListItem {
    data class FlavorHeader(val flavor: Flavor) : RecipeListItem()
    data class RecipeItem(val recipe: Recipe) : RecipeListItem()
}
