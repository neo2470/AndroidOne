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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.alex.androidone.entity.FrameAnimationLoader;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by alex on 16-4-1.
 * Android Animation
 */
public class AnimActivity extends Activity implements FrameAnimationLoader.onCreateFrameAnimationListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);

        loadingView = (ImageView) findViewById(R.id.loadingAnim);

        ImageView foreAnim = (ImageView) findViewById(R.id.foreAnim);

        new FrameAnimLoader(foreAnim).execute(0);

        ImageView googleLoadingView = (ImageView) findViewById(R.id.googleLoadingAnim);
        FrameAnimationLoader animLoader = new FrameAnimationLoader(googleLoadingView, R.drawable.google_loading);
        animLoader.setOnCreateFrameAnimationListener(this);
        animLoader.start();

        imageView = (ImageView) findViewById(R.id.scaleView);

        objAnimView1 = (ImageView) findViewById(R.id.objAnimView1);
        objAnimView2 = (ImageView) findViewById(R.id.objAnimView2);
        objAnimView3 = (ImageView) findViewById(R.id.objAnimView3);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        AnimationDrawable animDrawable = (AnimationDrawable) loadingView.getBackground();
        if (hasFocus) {
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

        for (int i = 0; i < ANIM_FRAME_COUNT; ++i) {
            int x = i * ANIM_FRAME_WIDTH;
            int y = 0;
            Drawable frame = new BitmapDrawable(Bitmap.createBitmap(bmp, x, y, ANIM_FRAME_WIDTH, ANIM_FRAME_HEIGHT));
            frameAnim.addFrame(frame, ANIM_FRAME_INTERVAL);
        }

        frameAnim.setOneShot(false);

        return frameAnim;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.objAnimBtn1:
                ObjectAnimator objAnim1 = null;
                int flag = 0;
                if (null != view.getTag()) {
                    flag = (int) view.getTag();
                }

                if (0 == (flag & 1)) {
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
            case R.id.objAnimBtn2:
                ValueAnimator valueObj = ObjectAnimator.ofInt(objAnimView2, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
                valueObj.setDuration(1000);
                valueObj.setEvaluator(new ArgbEvaluator());
                valueObj.setRepeatCount(ValueAnimator.INFINITE);
                valueObj.setRepeatMode(ValueAnimator.REVERSE);
                valueObj.start();
                break;
            case R.id.objAnimBtn3:
                ObjectAnimator objAnim3 = ObjectAnimator.ofFloat(objAnimView3, "rotation", 0, 360);
                objAnim3.setDuration(3500);
                objAnim3.setInterpolator(new LinearInterpolator());
                objAnim3.setRepeatCount(ValueAnimator.INFINITE);
                objAnim3.setRepeatMode(ValueAnimator.RESTART);
                objAnim3.start();
                break;
            case R.id.aAnim:// alpha animation
                AlphaAnimation alphaAnim = new AlphaAnimation(0.1f, 1.0f);
                alphaAnim.setDuration(1000);
                alphaAnim.setRepeatCount(AlphaAnimation.INFINITE);
                alphaAnim.setRepeatMode(AlphaAnimation.REVERSE);
                alphaAnim.setInterpolator(new LinearInterpolator());
                imageView.clearAnimation();
                imageView.startAnimation(alphaAnim);
                break;
            case R.id.sAnim:// scale animation
                ScaleAnimation scaleAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnim.setDuration(500);
                scaleAnim.setFillAfter(false);
                scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                imageView.clearAnimation();
                imageView.startAnimation(scaleAnim);
                break;
            case R.id.rAnim:// rotate animation
                RotateAnimation rotateAnim = new RotateAnimation(0.0f, 360.f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(3000);
                rotateAnim.setRepeatCount(Animation.INFINITE);
                rotateAnim.setRepeatMode(Animation.RESTART);
                rotateAnim.setInterpolator(new LinearInterpolator());
                imageView.clearAnimation();
                imageView.startAnimation(rotateAnim);
                break;
            case R.id.tAnim:// translate animation
                TranslateAnimation tranAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                tranAnim.setDuration(1000);
                tranAnim.setInterpolator(new AnticipateOvershootInterpolator());
                tranAnim.setRepeatMode(Animation.REVERSE);
                tranAnim.setRepeatCount(Animation.INFINITE);
                imageView.clearAnimation();
                imageView.startAnimation(tranAnim);
                break;
        }
    }

    private ImageView imageView;
    private ImageView loadingView;
    private ImageView objAnimView1;
    private ImageView objAnimView2;
    private ImageView objAnimView3;

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Anim Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.alex.androidone/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Anim Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.alex.androidone/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
