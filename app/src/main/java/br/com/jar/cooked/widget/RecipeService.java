package br.com.jar.cooked.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import br.com.jar.cooked.R;
import br.com.jar.cooked.data.AppDatabase;
import br.com.jar.cooked.model.Recipe;

public class RecipeService extends IntentService {

    public static final String ACTION_SELECT_RECIPE = "br.com.jar.cooked.action.SELECT_RECIPE";
    public static final String EXTRA_RECIPE = "br.com.jar.cooked.extra.RECIPE";

    public RecipeService() {
        super("RecipeService");
    }

    public static void startActionSelectRecipe(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_SELECT_RECIPE);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        final String action = intent.getAction();

        if (ACTION_SELECT_RECIPE.equals(action)) {
            handleActionSelectRecipe((Recipe) intent.getParcelableExtra(EXTRA_RECIPE));
        }
    }

    private void handleActionSelectRecipe(Recipe recipe) {
        updateRecipeStorage(recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_recipes_widget);
    }

    private void updateRecipeStorage(Recipe recipe) {
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        database.clearAllTables();
        database.recipeDao().insertRecipe(recipe);
    }

}
