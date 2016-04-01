package com.alex.androidone;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by alex on 16-4-1.
 * Android Animation
 */
public class AnimActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);

        ImageView foreAnim = (ImageView) findViewById(R.id.foreAnim);
        ImageView leftAnim = (ImageView) findViewById(R.id.leftAnim);
        ImageView rightAnim = (ImageView) findViewById(R.id.rightAnim);
        ImageView backAnim = (ImageView) findViewById(R.id.backAnim);

        new FrameAnimLoader(foreAnim).execute(0);
        new FrameAnimLoader(leftAnim).execute(1);
        new FrameAnimLoader(rightAnim).execute(2);
        new FrameAnimLoader(backAnim).execute(3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FrameAnimLoader.release();
    }
}

/**
 * FrameAnimation
 */
class FrameAnimLoader extends AsyncTask<Integer, Void, AnimationDrawable> {

    public FrameAnimLoader(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mResource = mImageView.getResources();
    }

    @Override
    protected AnimationDrawable doInBackground(Integer... params) {

        if (null == mBitmap) {
            mBitmap = BitmapFactory.decodeResource(mResource, R.drawable.on_foot_bmp);
        }

        final int ANIM_FRAME_WIDTH = 120;
        final int ANIM_FRAME_HEIGHT = 150;
        final int ANIM_FRAME_COUNT_HORIZONTAL = 8;
        final int ANIM_FRAME_INTERVAL = 100;

        AnimationDrawable frameAnim = new AnimationDrawable();

        for(int i=0; i<ANIM_FRAME_COUNT_HORIZONTAL; ++i) {

            int x = i * ANIM_FRAME_WIDTH;
            int y = params[0] * ANIM_FRAME_HEIGHT;

            Drawable frame = new BitmapDrawable(Bitmap.createBitmap(mBitmap, x, y, ANIM_FRAME_WIDTH, ANIM_FRAME_HEIGHT));
            frameAnim.addFrame(frame, ANIM_FRAME_INTERVAL);
        }

        return frameAnim;
    }

    @Override
    protected void onPostExecute(AnimationDrawable frameAnim) {
        super.onPostExecute(frameAnim);
        mImageView.setBackgroundDrawable(frameAnim);

        frameAnim.setOneShot(false);
        frameAnim.start();
    }

    protected static void release() {
        if (null != mBitmap) {
            if(!mBitmap.isRecycled()) {
                mBitmap.recycle();
                mBitmap = null;
            }
        }
    }

    private Resources mResource;
    private ImageView mImageView;
    private static Bitmap mBitmap;
}
