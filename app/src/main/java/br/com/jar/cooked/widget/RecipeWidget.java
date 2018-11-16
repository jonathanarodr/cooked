package br.com.jar.cooked.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import br.com.jar.cooked.R;
import br.com.jar.cooked.ui.MainActivity;

public class RecipeWidget extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views  = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        buildIntentService(context, views);
        buildPendingIntent(context, views);
        buildEmptyState(views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void buildIntentService(Context context, RemoteViews views) {
        views.setRemoteAdapter(R.id.lv_recipes_widget, new Intent(context, RecipeRemoteService.class));
    }

    private static void buildPendingIntent(Context context, RemoteViews views) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_recipes_widget, pendingIntent);
    }

    private static void buildEmptyState(RemoteViews views) {
        views.setEmptyView(R.id.lv_recipes_widget, R.id.rl_empty_state_widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

