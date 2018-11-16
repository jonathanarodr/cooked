package br.com.jar.cooked.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.jar.cooked.R;
import br.com.jar.cooked.data.AppDatabase;
import br.com.jar.cooked.data.RecipeDao;
import br.com.jar.cooked.model.Ingredient;
import br.com.jar.cooked.model.Recipe;
import br.com.jar.cooked.util.JsonUtils;

public class RecipeFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Recipe mRecipeSelected;
    private List<Recipe> mRecipes;

    public RecipeFactory(Context context) {
        mContext = context;
    }
    
    @Override
    public void onCreate() {
        buildJson();
    }

    @Override
    public void onDataSetChanged() {
        buildData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (mRecipeSelected == null) ? 0 : mRecipeSelected.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mRecipeSelected.getIngredients().get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_recipe_widget);
        views.setTextViewText(R.id.tv_ingredient_name_widget, ingredient.toString());

        Bundle bundle = new Bundle();
        bundle.putParcelable(Intent.EXTRA_INTENT, mRecipeSelected);
        views.setOnClickFillInIntent(R.id.tv_ingredient_name_widget, new Intent().putExtras(bundle));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void buildData() {
        RecipeDao dao = AppDatabase.getInstance(mContext).recipeDao();
        mRecipeSelected = dao.loadRecipe();

        if (mRecipeSelected == null) {
            return;
        }

        for (Recipe recipe : mRecipes) {
            if (mRecipeSelected.getId() == recipe.getId()) {
                mRecipeSelected = recipe;
                break;
            }
        }
    }

    private void buildJson() {
        String json = JsonUtils.loadAsset(mContext, "recipes.json");
        mRecipes = new Gson().fromJson(json, new TypeToken<List<Recipe>>(){}.getType());
    }

}
