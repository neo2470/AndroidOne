package com.alex.androidone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 16-5-18.
 */
public class LayoutAnimActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_anim);

        data1 = new ArrayList<>();
        data1.add("One");
        data1.add("Two");
        data1.add("Three");
        data1.add("Four");

        data2 = new ArrayList<>();
        data2.add("One");
        data2.add("Two");
        data2.add("Three");
        data2.add("Four");
        data2.add("Five");
        data2.add("Six");
        data2.add("Seven");
        data2.add("Eight");
        data2.add("Nine");
        data2.add("Ten");

        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data1);
        listView.setAdapter(listAdapter);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data2);
        gridView.setAdapter(gridAdapter);


        Animation slidInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        GridLayoutAnimationController gridLayoutAnimationController = new GridLayoutAnimationController(slidInLeft);
        gridLayoutAnimationController.setRowDelay(0.75f);
        gridLayoutAnimationController.setColumnDelay(0.5f);
        gridLayoutAnimationController.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP|GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT);
        gridLayoutAnimationController.setDirectionPriority(GridLayoutAnimationController.PRIORITY_NONE);
        gridView.setLayoutAnimation(gridLayoutAnimationController);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listBtn :
                ++count1;
                data1.add(0, "Add " + count1);
                listAdapter.notifyDataSetChanged();
                break;
            case R.id.gridBtn :
                ++count2;
                data2.add(0, "Add " + count2);
                gridAdapter.notifyDataSetChanged();
                break;
        }
    }

    private int count1;
    private int count2;
    private List<String> data1;
    private List<String> data2;
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> gridAdapter;
}
