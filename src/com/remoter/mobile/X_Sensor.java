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

	private String MOUSE_FLAG = "m";
	private String GRYO_FLAG = "g";
	private String GRYO_REG = "r";
	private String GRYO_UNREG = "u";
	private String ACCE_FLAG = "a";
	private String ACCE_REG = "r";
	private String ACCE_UNREG = "u";
	private String SENSOR_PRESS = "prs";
	private String SENSOR_RELEASE = "rls";
	private String SENSOR_MID = "mid";
	private String SENSOR_LFET = "lef";
	private String SENSOR_RIGHT = "rig";
	
	
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
			mSocket.sendData(MOUSE_FLAG+ACCE_FLAG+ACCE_REG);
			sensor = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if(DBG) Log.i(TAG, "Accelerometer mode.");
		} else {
			isGryo = true;
			mSocket.sendData(MOUSE_FLAG+GRYO_FLAG+GRYO_REG);
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
			if(isGryo)mSocket.sendData(MOUSE_FLAG+GRYO_FLAG+GRYO_UNREG);
			else mSocket.sendData(MOUSE_FLAG+ACCE_FLAG+ACCE_REG);
			mSensorManager.unregisterListener(this);
			isRegister = false;
			if (DBG)
				Log.i(TAG, "Sensors_unregister.");
		}
	}

	public void resetSensorData(){
		vx = (float)0.0;
		vy = (float)0.0;
		vz = (float)0.0;
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
//				float tmp_x = (float) (Math.toDegrees(-1*vz) * 1366 / 90.0);
//				float tmp_y = (float) (Math.toDegrees(-1*vx) * 768 / 90.0);
//				command = "mx" + tmp_x + "y" + tmp_y;
				command = "mgx" + Math.toDegrees(-1*vz) + "y" + Math.toDegrees(-1*vx);
			} else {
				command = "max" + Math.toDegrees(vz) + "y" + Math.toDegrees(vx);
			}
			mSocket.sendData(command);
		}
		timestamp = event.timestamp;
	}
	
	public void leftclick(){
		if(isGryo){
			command = MOUSE_FLAG + GRYO_FLAG ;
			mSocket.sendData(command);
		}else{
			command = MOUSE_FLAG + ACCE_FLAG ;
			mSocket.sendData(command);
		}
	}
	
	public void rightclick(){
		if(isGryo){
			command = MOUSE_FLAG + GRYO_FLAG ;
			mSocket.sendData(command);
		}else{
			command = MOUSE_FLAG + ACCE_FLAG ;
			mSocket.sendData(command);
		}
	}
	
	public void midclick(){
		if(isGryo){
			command = MOUSE_FLAG + GRYO_FLAG ;
			mSocket.sendData(command);
		}else{
			command = MOUSE_FLAG + ACCE_FLAG ;
			mSocket.sendData(command);
		}
	}
}
