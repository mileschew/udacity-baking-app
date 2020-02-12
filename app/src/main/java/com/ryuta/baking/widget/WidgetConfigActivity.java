package com.ryuta.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ryuta.baking.R;
import com.ryuta.baking.fragments.RecipeSelectFragment;
import com.ryuta.baking.models.Recipe;

public class WidgetConfigActivity extends AppCompatActivity {

    private static final int NUMBER_COLUMNS_PHONE = 1;
    private static final int NUMBER_COLUMNS_TABLET = 3;

    private int appWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_config);

        // in case user backs out instead of creating widget
        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, result);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;
        appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
        );

        int columnCount = NUMBER_COLUMNS_PHONE;
        if (getResources().getBoolean(R.bool.isTablet))     // add more columns if using a tablet
            columnCount = NUMBER_COLUMNS_TABLET;

        Fragment fragment = RecipeSelectFragment.newInstance(columnCount)
                .setOnRecipeSelectedListener(new RecipeSelectFragment.OnRecipeSelectedListener() {
                    @Override
                    public void onRecipeSelected(Recipe selected) {
                        // Create and update widget
                        RemoteViews views = new RemoteViews(getPackageName(), R.layout.view_widget);
                        AppWidgetManager.getInstance(WidgetConfigActivity.this).updateAppWidget(appWidgetId, views);
                        Intent result = new Intent();
                        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_select_container, fragment)
                .commit();
    }
}
