package br.com.jar.cooked.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.jar.cooked.R;
import br.com.jar.cooked.model.Ingredient;
import br.com.jar.cooked.model.Recipe;
import br.com.jar.cooked.model.Step;

public class DetailFragment extends Fragment implements StepAdapterOnClickHandler {

    private Recipe mRecipe;
    private TextView mRecipeName;
    private TextView mServings;
    private TextView mIngredients;
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mStepAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        bindViews(rootView);
        bindExtra();
        buildStepAdapter();

        return rootView;
    }

    private void bindViews(View rootView) {
        mRecipeName = rootView.findViewById(R.id.tv_recipe_name);
        mServings = rootView.findViewById(R.id.tv_servings);
        mIngredients = rootView.findViewById(R.id.tv_ingredients);
        mStepsRecyclerView = rootView.findViewById(R.id.rv_steps);
    }

    private void bindExtra() {
        if (getArguments().isEmpty() || getArguments().getParcelable(Intent.EXTRA_INTENT) == null) {
            return;
        }

        mRecipe = getArguments().getParcelable(Intent.EXTRA_INTENT);
        mRecipeName.setText(mRecipe.getName());
        mServings.setText(String.valueOf(mRecipe.getServings()));

        for (Ingredient ingredient : mRecipe.getIngredients()) {
            mIngredients.append(ingredient.toString());
        }
    }

    private void buildStepAdapter() {
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsRecyclerView.setHasFixedSize(true);
        mStepAdapter = new StepAdapter(this, mRecipe.getSteps());
        mStepsRecyclerView.setAdapter(mStepAdapter);
    }

    @Override
    public void onClick(Step step) {
        Intent intent = new Intent(getContext(), StepActivity.class);
        intent.putExtra(Intent.EXTRA_INTENT, step);

        startActivity(intent);
    }

}
