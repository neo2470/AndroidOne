package com.alex.androidone.entity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by alex on 16-3-31.
 * 追蹤器
 */
public class Tracker {

    public interface OnTraceListener {
        void onTrace(String path);
    }

    public Tracker(Context context, String tag) {
        TAG = tag;
        mBuilder = new StringBuilder();
        mContext = context;
    }

    public void setOnTraceListener(OnTraceListener mListener) {
        this.mListener = mListener;
    }

    public void trace(String content) {
        ++line;
        String out = String.format("Console # %02d : %s", line, content);
        Log.d("Debug-" + TAG, out);

        if(SHOW_TOAST) {
            Toast.makeText(mContext, out, Toast.LENGTH_SHORT).show();
        }

        mBuilder.append(out);
        mBuilder.append("\n");

        if(null != mListener) {
            mListener.onTrace(mBuilder.toString());
        } else {
            throw new NullPointerException();
        }
    }

    private int line;
    private Context mContext;
    private StringBuilder mBuilder;
    private OnTraceListener mListener;

    private final String TAG;
    private static final boolean SHOW_TOAST = true;
}
