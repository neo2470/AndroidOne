package com.alex.androidone;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.androidone.entity.FrameAnimationLoader;

/**
 * Created by alex on 16-4-1.
 * Android Animation
 */
public class AnimActivity extends Activity implements FrameAnimationLoader.onCreateFrameAnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);

        loadingView = (ImageView) findViewById(R.id.loadingAnim);

        ImageView foreAnim = (ImageView) findViewById(R.id.foreAnim);
//        ImageView leftAnim = (ImageView) findViewById(R.id.leftAnim);
//        ImageView rightAnim = (ImageView) findViewById(R.id.rightAnim);
//        ImageView backAnim = (ImageView) findViewById(R.id.backAnim);

        new FrameAnimLoader(foreAnim).execute(0);
//        new FrameAnimLoader(leftAnim).execute(1);
//        new FrameAnimLoader(rightAnim).execute(2);
//        new FrameAnimLoader(backAnim).execute(3);

        ImageView googleLoadingView = (ImageView) findViewById(R.id.googleLoadingAnim);
        FrameAnimationLoader animLoader = new FrameAnimationLoader(googleLoadingView, R.drawable.google_loading);
        animLoader.setOnCreateFrameAnimationListener(this);
        animLoader.start();

        ImageView scaleView = (ImageView) findViewById(R.id.scaleView);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.avator_set_in);
        scaleView.startAnimation(scaleAnim);

        TextView tranText = (TextView) findViewById(R.id.tranText);
        Animation tranAnim = AnimationUtils.loadAnimation(this, R.anim.text_translate_in);
        tranText.startAnimation(tranAnim);

        objAnimView1 = (ImageView) findViewById(R.id.objAnimView1);
        objAnimView2 = (ImageView) findViewById(R.id.objAnimView2);
        objAnimView3 = (ImageView) findViewById(R.id.objAnimView3);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        AnimationDrawable animDrawable = (AnimationDrawable) loadingView.getBackground();
        if(hasFocus) {
            animDrawable.start();
        } else {
            animDrawable.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FrameAnimLoader.release();
    }

    @Override
    public AnimationDrawable onCreateFrames(Bitmap bmp) {
        AnimationDrawable frameAnim = new AnimationDrawable();

        final int ANIM_FRAME_WIDTH = 64;
        final int ANIM_FRAME_HEIGHT = 64;
        final int ANIM_FRAME_COUNT = 32;
        final int ANIM_FRAME_INTERVAL = 70;

        for(int i=0; i<ANIM_FRAME_COUNT; ++i) {
            int x = i*ANIM_FRAME_WIDTH;
            int y = 0;
            Drawable frame = new BitmapDrawable(Bitmap.createBitmap(bmp, x, y, ANIM_FRAME_WIDTH, ANIM_FRAME_HEIGHT));
            frameAnim.addFrame(frame, ANIM_FRAME_INTERVAL);
        }

        frameAnim.setOneShot(false);

        return frameAnim;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.objAnimBtn1 :
                ObjectAnimator objAnim1 = null;
                int flag = 0;
                if(null != view.getTag()) {
                    flag = (int) view.getTag();
                }

                if(0 == (flag&1)) {
                    objAnim1 = ObjectAnimator.ofFloat(objAnimView1, "translationY", -objAnimView1.getHeight());
                    flag = 1;
                } else {
                    objAnim1 = ObjectAnimator.ofFloat(objAnimView1, "translationY", 0);
                    flag = 0;
                }

                objAnim1.setInterpolator(new AccelerateDecelerateInterpolator());
                objAnim1.setDuration(200);
                objAnim1.start();
                view.setTag(flag);
                break;
            case R.id.objAnimBtn2 :
                ValueAnimator valueObj = ObjectAnimator.ofInt(objAnimView2, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
                valueObj.setDuration(1000);
                valueObj.setEvaluator(new ArgbEvaluator());
                valueObj.setRepeatCount(ValueAnimator.INFINITE);
                valueObj.setRepeatMode(ValueAnimator.REVERSE);
                valueObj.start();
                break;
            case R.id.objAnimBtn3 :
                ObjectAnimator objAnim3 = ObjectAnimator.ofFloat(objAnimView3, "rotation", 0, 360);
                objAnim3.setDuration(3500);
                objAnim3.setInterpolator(new LinearInterpolator());
                objAnim3.setRepeatCount(ValueAnimator.INFINITE);
                objAnim3.setRepeatMode(ValueAnimator.RESTART);
                objAnim3.start();
                break;
        }
    }

    private ImageView loadingView;
    private ImageView objAnimView1;
    private ImageView objAnimView2;
    private ImageView objAnimView3;
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
