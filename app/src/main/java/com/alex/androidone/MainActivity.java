package com.alex.androidone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by alex on 16-3-31.
 * app主Activity
 */
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.topics));
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class<? extends Activity> toStart = null;
        switch (position) {
            case 0 :// Activity Lifecycle
                toStart = ActivityLifecycle.class;
                break;
            case 1 :// Animation
                toStart = AnimActivity.class;
                break;
            case 2 :
                ;
                break;
        }

        if(null != toStart) {
            Intent intent = new Intent(this, toStart);
            startActivity(intent);
        }
    }
}
