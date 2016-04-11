package com.alex.androidone.entity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by alex on 16-4-7.
 * Load all frames from only one bitmap for an ImageView
 */
public class FrameAnimationLoader {

    public interface onCreateFrameAnimationListener {
        AnimationDrawable onCreateFrames(Bitmap bmp);
    }

    public FrameAnimationLoader(ImageView mImageView, int mResId) {
        this.mImageView = mImageView;
        this.mResId = mResId;
    }

    public void setOnCreateFrameAnimationListener(onCreateFrameAnimationListener mListener) {
        this.mListener = mListener;
    }

    public void start() {
        new AsyncTask<Void, Void, AnimationDrawable>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mResource = mImageView.getResources();
            }

            @Override
            protected AnimationDrawable doInBackground(Void... params) {
                Bitmap bmp = BitmapFactory.decodeResource(mResource, mResId);

                if(null == mListener) {
                    throw new NullPointerException();
                }

                return mListener.onCreateFrames(bmp);
            }

            @Override
            protected void onPostExecute(AnimationDrawable animationDrawable) {
                super.onPostExecute(animationDrawable);
                mImageView.setBackgroundDrawable(animationDrawable);
                animationDrawable.start();
            }

            private Resources mResource;
        }.execute();
    }

    private int mResId;
    private ImageView mImageView;

    private onCreateFrameAnimationListener mListener;
}
