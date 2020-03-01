package com.ryuta.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ryuta.baking.R;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.RecipeRepository;

import java.util.ArrayList;

public class RecipeWidgetService extends RemoteViewsService {

    public static final String EXTRA_RECIPE = "recipe";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeStepItemFactory(getApplicationContext(), intent);
    }

    class RecipeStepItemFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;    // if we have multiple instances of widget
        private int recipeId;
        private ArrayList<Step> steps;

        RecipeStepItemFactory(Context context, Intent intent) {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            recipeId = intent.getIntExtra(RecipeWidgetService.EXTRA_RECIPE, 0);
        }

        @Override
        public void onCreate() {
            ArrayList<Recipe> recipes = RecipeRepository.getRecipes(context);
            Recipe recipe = RecipeRepository.getRecipe(recipes, recipeId);
            steps = (recipe != null) ? (ArrayList<Step>) recipe.getSteps() : new ArrayList<Step>();
        }

        @Override
        public void onDataSetChanged() { }

        @Override
        public void onDestroy() { }

        @Override
        public int getCount() {
            return Math.max(steps.size() - 1, 0);    // we're skipping the first step
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget_step);
            views.setTextViewText(R.id.widget_step_description, steps.get(position + 1).getDescription());
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
    }
}
