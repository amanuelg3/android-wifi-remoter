package com.remoter.mobile;

import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class X_Sensor implements SensorEventListener {

	private boolean DBG = true;
	private String TAG = "X_Sensor";

	private Socket_UDP mSocket;
	public String command = null;
	private boolean isRegister;
	private SensorManager mSensorManager;
	private boolean isGryo = true;
	private static final float NS2S = 1.0f / 1000000000.0f;
	// private final float[] deltaRotationVector = new float[4]();
	private float vx = 0;
	private float vy = 0;
	private float vz = 0;
	private float timestamp;

	public X_Sensor(SensorManager sensorManager, Thread socket) {
		isRegister = false;
		mSensorManager = sensorManager;
		mSocket = (Socket_UDP) socket;
	}

	public void Sensors_register() {
		List<Sensor> sensor;
		if (mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE) == null) {
			isGryo = false;
			sensor = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if(DBG) Log.i(TAG, "Accelerometer mode.");
		} else {
			isGryo = true;
			sensor = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
			if(DBG) Log.i(TAG, "Gryoscope mode.");
		}
		if ((sensor.size() > 0) && (!isRegister)) {
			if (DBG) Log.i(TAG, "Sensors_register");
			Sensor asensor = (Sensor) sensor.get(0);
			isRegister = mSensorManager.registerListener(this, asensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
		
		vx = 0;
		vy = 0;
		vz = 0;
	}

	public void Sensors_unregister() {
		if (isRegister) {
			mSensorManager.unregisterListener(this);
			isRegister = false;
			if (DBG)
				Log.i(TAG, "Sensors_unregister.");
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		if (timestamp != 0) {
			final float dT = (event.timestamp - timestamp) * NS2S;
			float axisX = event.values[0];
			float axisY = event.values[1];
			float axisZ = event.values[2];
			if (isGryo) { // vx is angrad
				vx += axisX * dT;
				vy += axisY * dT;
				vz += axisZ * dT;
				float tmp_x = (float) (Math.toDegrees(-1*vz) * 1366 / 90.0);
				float tmp_y = (float) (Math.toDegrees(-1*vx) * 768 / 90.0);
				command = "mx" + tmp_x + "y" + tmp_y;
			} else {
				
				command = "max" + Math.toDegrees(vz) + "y" + Math.toDegrees(vx);
			}
			mSocket.sendData(command);
		}
		timestamp = event.timestamp;
	}
}
