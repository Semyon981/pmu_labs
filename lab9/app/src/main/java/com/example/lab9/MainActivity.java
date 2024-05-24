package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Chronometer chronometer;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button addCompButton;

    private ConstraintLayout container;
    long timeWhenStopped = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = (Chronometer)findViewById(R.id.chronometer);

        container = findViewById(R.id.container);

        startButton = findViewById(R.id.button_start);
        stopButton = findViewById(R.id.button_stop);
        resetButton = findViewById(R.id.button_reset);
        addCompButton = findViewById(R.id.button_add_comp);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        addCompButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == startButton) {
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
        }
        else if (v == stopButton) {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
        }
        else if (v == resetButton) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenStopped = 0;
        } else if (v == addCompButton){
            TextView text = new TextView(this);
            text.setId(View.generateViewId()); // Ensure the TextView has a unique ID
            text.setText("Dynamic TextView");
            text.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            container.addView(text);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(container);
            constraintSet.connect(text.getId(), ConstraintSet.TOP, addCompButton.getId(), ConstraintSet.BOTTOM, 16);
            constraintSet.connect(text.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(text.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.applyTo(container);
        }
    }
}
