package br.com.jar.cooked.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jar.cooked.R;
import br.com.jar.cooked.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private final RecipeAdapterOnClickHandler mClickHandler;
    private List<Recipe> mRecipes;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, List<Recipe> recipes) {
        mClickHandler = clickHandler;
        mRecipes = recipes;
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRecipeName;

        RecipeHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(mRecipes.get(getAdapterPosition()));
        }

    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe, viewGroup, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int i) {
        Recipe recipe = mRecipes.get(i);
        recipeHolder.mRecipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return (mRecipes == null) ? 0 : mRecipes.size();
    }

}
