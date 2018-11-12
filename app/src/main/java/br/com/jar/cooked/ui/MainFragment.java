package br.com.jar.cooked.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.jar.cooked.R;
import br.com.jar.cooked.model.Recipe;
import br.com.jar.cooked.util.JsonUtils;

public class MainFragment extends Fragment implements RecipeAdapterOnClickHandler {

    private RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private List<Recipe> mRecipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master, container, false);

        bindViews(rootView);
        buildData();
        buildAdapter();
        filterRecipe();

        return rootView;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (((MainActivity)getActivity()).isLayoutTwoPane()) {
            return new GridLayoutManager(getContext(), 3);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }

    private void buildData() {
        String json = JsonUtils.loadAsset(getContext(), "recipes.json");
        mRecipes = new Gson().fromJson(json, new TypeToken<List<Recipe>>(){}.getType());
    }

    private void bindViews(View rootView) {
        mRecipesRecyclerView = rootView.findViewById(R.id.rv_recipes);
    }

    private void buildAdapter() {
        mRecipesRecyclerView.setLayoutManager(getLayoutManager());
        mRecipesRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this, mRecipes);
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);
    }

    private void filterRecipe() {
        if (getActivity() == null || !getActivity().getIntent().hasExtra(Intent.EXTRA_INTENT)) {
            return;
        }

        Recipe recipe = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_INTENT);
        getActivity().getIntent().removeExtra(Intent.EXTRA_INTENT);

        onClick(recipe);
    }

    @Override
    public void onClick(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Intent.EXTRA_INTENT, recipe);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_recipes, detailFragment)
                .addToBackStack(null)
                .commit();

        ((MainActivity) getActivity()).addBackStackListener();
    }

}
