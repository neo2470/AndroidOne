package com.alex.androidone;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by alex on 16-4-1.
 */
public class ActivityLifecycle1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_1);
    }
}
