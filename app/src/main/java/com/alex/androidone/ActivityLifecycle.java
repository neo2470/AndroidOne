package com.alex.androidone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.alex.androidone.entity.Tracker;

/**
 * Created by alex on 16-3-31.
 * Activity生命週期
 */
public class ActivityLifecycle extends Activity implements Tracker.OnTraceListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        mTerminal = (TextView) findViewById(R.id.terminalText);
        mTerminal.setMovementMethod(new ScrollingMovementMethod());

        findViewById(R.id.anActivity).setOnClickListener(this);
        findViewById(R.id.anActivityDialog).setOnClickListener(this);
        findViewById(R.id.shwDialog).setOnClickListener(this);

        if(null == mTracker) {
            mTracker = new Tracker(this, TAG);
        }

        mTracker.setOnTraceListener(this);

        mTracker.trace("onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mTracker.trace("onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.trace("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.trace("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTracker.trace("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTracker.trace("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTracker.trace("onDestroy()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mTracker.trace("onConfigurationChanged()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTracker.trace("onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTracker.trace("onRestoreInstanceState()");
    }

    @Override
    public void onTrace(String path) {
        mTerminal.setText(path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anActivity :
                startActivity(new Intent(this, ActivityLifecycle1.class));
                break;
            case R.id.anActivityDialog :
                startActivity(new Intent(this, ActivityLifecycle2.class));
                break;
            case R.id.shwDialog :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Title");
                builder.setMessage("Show Dialog to demonstrate Activity lifecycle");
                builder.create().show();
                break;
        }
    }

    private static Tracker mTracker;
    private TextView mTerminal;

    private static final String TAG = "ActivityLifecycle";
}
