package com.example.lab10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private RatingBar ratingBar;
    private TextView text;
    private ToggleButton button;
    private boolean isRunning = false;
    private static final int NUM_STARS = 5;
    private float step = 0.5f;
    private float rating = 1.0f;
    private Thread background;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            progressBar.incrementProgressBy(1);
            text.setText("Progress: " + progressBar.getProgress() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        text = findViewById(R.id.text);
        ratingBar = findViewById(R.id.ratingBar);
        button = findViewById(R.id.button_start);
        TextView text_rating = findViewById(R.id.text_rating);

        ratingBar.setNumStars(NUM_STARS);
        ratingBar.setRating(rating);
        ratingBar.setStepSize(step);
        progressBar.setProgress(0);
        text_rating.setText(String.valueOf(rating));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                text_rating.setText(String.valueOf(ratingBar.getRating()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int but = v.getId();
        if (but == R.id.button_start) {
            if (button.isChecked()) {
                background = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isRunning) {
                            try {
                                Thread.sleep((long) (1000 / ratingBar.getRating()));
                                handler.post(updateProgress);
                            } catch (InterruptedException e) {
                                Log.e("ERROR", "Thread Interrupted");
                            }
                        }
                    }
                });
                isRunning = true;
                background.start();
            } else {
                isRunning = false;
            }
        } else if (but == R.id.button_reset) {
            isRunning = false;
            button.setChecked(false);
            progressBar.setProgress(0);
            text.setText("Progress: 0%");
        } else if (but == R.id.button_down) {
            rating -= step;
            if (rating < 0.5) rating = 0.5F;
            ratingBar.setRating(rating);
        } else if (but == R.id.button_up) {
            rating += step;
            if (rating > NUM_STARS) rating = NUM_STARS;
            ratingBar.setRating(rating);
        }
    }
}
