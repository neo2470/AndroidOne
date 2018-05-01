package com.alex.androidone;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.androidone.entity.FrameAnimationLoader;
import com.alex.androidone.view.CircleView;

/**
 * Created by alex on 16-4-1.
 * Android Animation
 */
public class AnimActivity extends Activity implements FrameAnimationLoader.onCreateFrameAnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

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
        objAnimView4 = (TextView) findViewById(R.id.objAnimView4);
        objAnimView5 = (CircleView) findViewById(R.id.objAnimView5);
        objAnimView6 = (TextView) findViewById(R.id.objAnimView6);
        objAnimView7 = (CircleView) findViewById(R.id.objAnimView7);
        objAnimView8 = (ImageView) findViewById(R.id.objAnimView8);
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
            case R.id.objAnimBtn4:
                ValueAnimator objAnim4 = ValueAnimator.ofObject(new TypeEvaluator() {
                    @Override
                    public Object evaluate(float fraction, Object startValue, Object endValue) {

                        int sv = (int)((Character)startValue);
                        int ev = (int)((Character)endValue);

                        return (char)((int) (sv + fraction * (ev - sv)));
                    }
                }, 'A', 'Z');
                objAnim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        char text = (Character) animation.getAnimatedValue();
                        objAnimView4.setText(text+"");
                    }
                });
                objAnim4.setDuration(10000);
                objAnim4.setInterpolator(new LinearInterpolator());
                objAnim4.start();
                break;
            case R.id.objAnimBtn5:
                objAnimView5.playAnim();
                break;
            case R.id.objAnimBtn6:
                ObjectAnimator objAnim6 = ObjectAnimator.ofInt(objAnimView6, "backgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
                objAnim6.setDuration(3000);
                objAnim6.setEvaluator(new ArgbEvaluator());
                objAnim6.start();
                break;
            case R.id.objAnimBtn7:
                ObjectAnimator objAnim7 = ObjectAnimator.ofInt(objAnimView7, "CircleRadius", 0, 50, 0, 50);
                objAnim7.setDuration(3000);
                objAnim7.setInterpolator(new LinearInterpolator());
                objAnim7.start();
                break;
            case R.id.objAnimBtn8:
                Keyframe frame0 = Keyframe.ofFloat(0f, 0);
                Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
                Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
                Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
                Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
                Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
                Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
                Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
                Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
                Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
                Keyframe frame10 = Keyframe.ofFloat(1, 0);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);

                Animator animator = ObjectAnimator.ofPropertyValuesHolder(objAnimView8, propertyValuesHolder);
                animator.setDuration(3000);
                animator.start();
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
    private TextView objAnimView4;
    private CircleView objAnimView5;
    private TextView objAnimView6;
    private CircleView objAnimView7;
    private ImageView objAnimView8;
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
