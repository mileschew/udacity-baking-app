package com.ryuta.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.ActivityWidgetConfigBinding;
import com.ryuta.baking.fragments.RecipeSelectFragment;
import com.ryuta.baking.models.Recipe;

public class WidgetConfigActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "recipePrefs";
    public static final String PREF_RECIPE_ID = "recipeId";

    private static final int NUMBER_COLUMNS_PHONE = 1;
    private static final int NUMBER_COLUMNS_TABLET = 3;

    private ActivityWidgetConfigBinding binding;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_widget_config);

        // in case user backs out instead of creating widget
        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, result);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            finish();
        appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
        );

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();       // something went wrong
        }

        int columnCount = NUMBER_COLUMNS_PHONE;
        if (getResources().getBoolean(R.bool.isTablet))     // add more columns if using a tablet
            columnCount = NUMBER_COLUMNS_TABLET;

        Fragment fragment = RecipeSelectFragment.newInstance(columnCount)
                .setOnRecipeSelectedListener(new RecipeSelectFragment.OnRecipeSelectedListener() {
                    @Override
                    public void onRecipeSelected(Recipe selected) {
                        createWidget(selected);
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_select_container, fragment)
                .commit();

        // Set up toolbar
        binding.actionbar.setTitle(getString(R.string.widget_recipe_select_title));
        setSupportActionBar(binding.actionbar);
    }

    private void createWidget(Recipe selectedRecipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // Button to re-open WidgetConfigActivity
        Intent reconfigIntent = new Intent(this, WidgetConfigActivity.class);
        reconfigIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent reconfigPendingIntent = PendingIntent.getActivity(this, 0, reconfigIntent, 0);

        // set data for list
        Intent serviceIntent = new Intent(this, RecipeWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.putExtra(RecipeWidgetService.EXTRA_RECIPE, selectedRecipe.getId());
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.view_widget);
        views.setOnClickPendingIntent(R.id.widget_reconfig, reconfigPendingIntent);
        views.setTextViewText(R.id.widget_title, selectedRecipe.getName());
        views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
        views.setEmptyView(R.id.widget_stack_view, R.id.widget_empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        // save selection in prefs
        getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
                .edit()
                .putInt(PREF_RECIPE_ID + appWidgetId, selectedRecipe.getId())
                .apply();

        // Set successful result
        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, result);
        finish();
    }
}
