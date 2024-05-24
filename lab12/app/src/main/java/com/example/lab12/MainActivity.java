package com.example.lab12;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private TransitionDrawable mTransition;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TransitionDrawable
        ImageView transitionImage = findViewById(R.id.transition_image);
        mTransition = (TransitionDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.transition, null);
        transitionImage.setImageDrawable(mTransition);
        transitionImage.setOnClickListener(v -> {
            if (flag) {
                mTransition.startTransition(2000);
                flag = false;
            } else {
                mTransition.reverseTransition(2000);
                flag = true;
            }
        });

        // Frame Animation
        ImageView frameAnimationImage = findViewById(R.id.frame_animation_image);
        AnimationDrawable frameAnimation = (AnimationDrawable) frameAnimationImage.getDrawable();
        frameAnimation.start();

        // Transformation Animation
        View shapeView = findViewById(R.id.shape_view);
        Animation transformAnimation = AnimationUtils.loadAnimation(this, R.anim.transform_animation);
        shapeView.startAnimation(transformAnimation);

        shapeView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shapeView.startAnimation(transformAnimation);
            }
        });

    }
}