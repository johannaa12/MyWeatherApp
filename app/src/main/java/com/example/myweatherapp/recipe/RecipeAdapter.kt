package com.example.myweatherapp.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_FLAVOR = 0
        private const val TYPE_RECIPE = 1
    }

    private val items = mutableListOf<RecipeListItem>(
        RecipeListItem.FlavorHeader(Flavor.SAVORY),
        RecipeListItem.FlavorHeader(Flavor.SWEET)
    )

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is RecipeListItem.FlavorHeader -> TYPE_FLAVOR
            is RecipeListItem.RecipeItem -> TYPE_RECIPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_FLAVOR) {
            val view = inflater.inflate(R.layout.item_flavor_header, parent, false)
            FlavorViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_recipe_title, parent, false)
            RecipeViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is RecipeListItem.FlavorHeader -> (holder as FlavorViewHolder).bind(item.flavor)
            is RecipeListItem.RecipeItem -> (holder as RecipeViewHolder).bind(item.recipe, onRecipeClick)
        }
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): RecipeListItem? {
        return if (position in 0 until items.size) items[position] else null
    }

    fun addRecipe(recipe: Recipe) {
        val newItem = RecipeListItem.RecipeItem(recipe)
        if (recipe.flavor == Flavor.SAVORY) {
            val sweetHeaderIndex = items.indexOfFirst {
                it is RecipeListItem.FlavorHeader && it.flavor == Flavor.SWEET
            }
            val insertIndex = if (sweetHeaderIndex != -1) sweetHeaderIndex else items.size
            items.add(insertIndex, newItem)
            notifyItemInserted(insertIndex)
        } else {
            items.add(newItem)
            notifyItemInserted(items.size - 1)
        }
    }

    fun removeAt(position: Int) {
        if (position in 0 until items.size) {
            val item = items[position]
            if (item is RecipeListItem.RecipeItem) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    class FlavorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.flavor_title)

        fun bind(flavor: Flavor) {
            titleTextView.text = flavor.displayName
        }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.recipe_title)

        fun bind(recipe: Recipe, onClick: (Recipe) -> Unit) {
            titleTextView.text = recipe.title
            itemView.setOnClickListener { onClick(recipe) }
        }
    }
}
