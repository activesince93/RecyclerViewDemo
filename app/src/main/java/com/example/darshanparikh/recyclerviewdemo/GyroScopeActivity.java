package com.example.darshanparikh.recyclerviewdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by darshan.parikh on 25-Aug-15.
 */
public class GyroScopeActivity extends AppCompatActivity implements SensorEventListener{

    private TextView txtInfo;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        txtInfo.setText("Orientation X (Roll) :"+ Float.toString(sensorEvent.values[2]) +"\n"+
                "Orientation Y (Pitch) :"+ Float.toString(sensorEvent.values[1]) +"\n"+
                "Orientation Z (Yawn) :"+ Float.toString(sensorEvent.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
