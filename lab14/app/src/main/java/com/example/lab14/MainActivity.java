package com.example.lab14;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private TextView touchInfo;
    private RelativeLayout gestureContainer;
    private Random random = new Random();

    long prevScrollTime = 0;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchInfo = findViewById(R.id.touch_info);
        gestureContainer = findViewById(R.id.gesture_container);

        gestureDetector = new GestureDetector(this, this);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchInfo.setText(event.getX() + ", " + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                touchInfo.setText(event.getX() + ", " + event.getY());
                break;
            case MotionEvent.ACTION_UP:
                touchInfo.setText("");
                break;
        }

        return super.onTouchEvent(event);
    }

    private void showGestureMessage(String message, float x, float y) {
        final TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setTextSize(30);
        messageView.setTextColor(getResources().getColor(android.R.color.white));

        // Measure the size of the TextView
        messageView.measure(0, 0);
        int textWidth = messageView.getMeasuredWidth();
        int textHeight = messageView.getMeasuredHeight();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) x - textWidth / 2;
        params.topMargin = (int) y - textHeight / 2;

        gestureContainer.addView(messageView, params);

        // Create the animations

        ObjectAnimator translateY = ObjectAnimator.ofFloat(messageView, "translationY", 0f, screenHeight / 2 - y);
        translateY.setDuration(700);
        translateY.setInterpolator(new AccelerateInterpolator(1.5f));

        float toXDelta = random.nextInt(2001) - 1000;
        ObjectAnimator translateX = ObjectAnimator.ofFloat(messageView, "translationX", 0f, toXDelta);
        translateX.setDuration(700);
        translateX.setInterpolator(new AccelerateInterpolator(1.5f));

        ObjectAnimator alpha = ObjectAnimator.ofFloat(messageView, "alpha", 1f, 0f);
        alpha.setDuration(600);
        alpha.setStartDelay(100);

        float randAngle = random.nextInt(201) - 100; // Random angle between -100 and 100 degrees
        ObjectAnimator rotate = ObjectAnimator.ofFloat(messageView, "rotation", 0f, randAngle);
        rotate.setDuration(700);

        // Combine the animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateY, translateX, alpha, rotate);
        animatorSet.start();

        messageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                gestureContainer.removeView(messageView);
            }
        }, 700);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        showGestureMessage("tap", e.getX(), e.getY());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        showGestureMessage("show", e.getX(), e.getY());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        showGestureMessage("single", e.getX(), e.getY());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (System.currentTimeMillis() - prevScrollTime > 50) {
            showGestureMessage("scroll", e2.getX(), e2.getY());
            prevScrollTime = System.currentTimeMillis();
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        showGestureMessage("long", e.getX(), e.getY());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        showGestureMessage("fling", e1.getX(), e1.getY());
        return true;
    }
}


