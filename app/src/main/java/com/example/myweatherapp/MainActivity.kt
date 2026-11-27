package com.example.myweatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.recipe.Flavor
import com.example.myweatherapp.recipe.Recipe
import com.example.myweatherapp.recipe.RecipeAdapter
import com.example.myweatherapp.recipe.RecipeListItem

class MainActivity : AppCompatActivity() {

    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var recipeTitleEditText: EditText
    private lateinit var recipeDescriptionEditText: EditText
    private lateinit var addSavoryButton: Button
    private lateinit var addSweetButton: Button

    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipesRecyclerView = findViewById(R.id.main_recipes_list)
        recipeTitleEditText = findViewById(R.id.main_recipe_title)
        recipeDescriptionEditText = findViewById(R.id.main_recipe_description)
        addSavoryButton = findViewById(R.id.main_add_savory_button)
        addSweetButton = findViewById(R.id.main_add_sweet_button)

        setupRecyclerView()
        setupAddButtons()
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(onRecipeClick = { recipe ->
            showRecipeDialog(recipe)
        })

        recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        recipesRecyclerView.adapter = adapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = adapter.getItem(position)
                    if (item is RecipeListItem.RecipeItem) {
                        adapter.removeAt(position)
                    } else {
                        adapter.notifyItemChanged(position)
                    }
                }
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                if (viewHolder is RecipeAdapter.FlavorViewHolder) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recipesRecyclerView)
    }

    private fun setupAddButtons() {
        addSavoryButton.setOnClickListener {
            addRecipe(Flavor.SAVORY)
        }

        addSweetButton.setOnClickListener {
            addRecipe(Flavor.SWEET)
        }
    }

    private fun addRecipe(flavor: Flavor) {
        val title = recipeTitleEditText.text.toString().trim()
        val description = recipeDescriptionEditText.text.toString().trim()

        if (title.isEmpty()) {
            recipeTitleEditText.error = "Please enter a recipe title"
            return
        }

        if (description.isEmpty()) {
            recipeDescriptionEditText.error = "Please enter a recipe description"
            return
        }

        val recipe = Recipe(title = title, description = description, flavor = flavor)
        adapter.addRecipe(recipe)

        // Clear input form after recipe is added
        recipeTitleEditText.text.clear()
        recipeDescriptionEditText.text.clear()
        recipeTitleEditText.clearFocus()
        recipeDescriptionEditText.clearFocus()
    }

    private fun showRecipeDialog(recipe: Recipe) {
        AlertDialog.Builder(this)
            .setTitle(recipe.title)
            .setMessage(recipe.description)
            .setPositiveButton("OK", null)
            .show()
    }
}
