package com.relyon.feedme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class CallActivity {

    public static void openActivity(Context context, Class activity) {
        ((Activity) context).finish();
        context.startActivity(new Intent(context, activity));
    }
}