package com.ryuta.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.ryuta.baking.R;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.RecipeRepository;

import java.util.ArrayList;

import static com.ryuta.baking.widget.WidgetConfigActivity.PREF_RECIPE_ID;
import static com.ryuta.baking.widget.WidgetConfigActivity.SHARED_PREFS;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        ArrayList<Recipe> recipes = RecipeRepository.getRecipes(context);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        for (int appWidgetId : appWidgetIds) {
            // retrieve saved recipe for this widget instance
            int recipeId = prefs.getInt(PREF_RECIPE_ID + appWidgetId, 0);
            Recipe recipe = RecipeRepository.getRecipe(recipes, recipeId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_widget);
            if (recipe != null) {
                // set data for list
                Intent serviceIntent = new Intent(context, RecipeWidgetService.class);
                serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                if (recipe != null)
                    serviceIntent.putExtra(RecipeWidgetService.EXTRA_RECIPE, recipeId);
                serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

                // Button to re-open WidgetConfigActivity
                Intent reconfigIntent = new Intent(context, WidgetConfigActivity.class);
                reconfigIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                PendingIntent reconfigPendingIntent = PendingIntent.getActivity(context, 0, reconfigIntent, 0);

                views.setOnClickPendingIntent(R.id.widget_reconfig, reconfigPendingIntent);
                views.setTextViewText(R.id.widget_title, recipe.getName());
                views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
                views.setEmptyView(R.id.widget_stack_view, R.id.widget_empty_view);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
