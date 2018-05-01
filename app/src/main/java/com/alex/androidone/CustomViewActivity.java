package com.alex.androidone;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by alex on 2018/3/16.
 *
 */

public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
