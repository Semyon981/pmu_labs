package com.example.lab15;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView gyroTextView;
    private TextView lightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroTextView = findViewById(R.id.gyroTextView);
        lightTextView = findViewById(R.id.lightTextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (gyroSensor != null) {
            sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            gyroTextView.setText("Gyroscope not available");
        }

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            lightTextView.setText("Light sensor not available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                gyroTextView.setText(String.format("Gyroscope\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
                break;
            case Sensor.TYPE_LIGHT:
                float lightLevel = event.values[0];
                lightTextView.setText(String.format("Light Level: %.2f lx", lightLevel));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach(sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
