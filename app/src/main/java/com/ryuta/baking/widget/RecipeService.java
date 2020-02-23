package com.ryuta.baking.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public class RecipeService extends IntentService {

    public static final String STEP_ACTION = "com.ryuta.baking.action.step";

    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;;
        if (intent.getAction() == STEP_ACTION) {

        }
    }

    public static void NextStep(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(STEP_ACTION);
        context.startService(intent);
    }
}
