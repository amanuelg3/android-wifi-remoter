package com.remoter.mobile;

import java.util.List;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

public class X_Sensor implements SensorEventListener {
public String command = null;
	public Context context = null;
	public Handler handler = null;
	public Runnable send_r = null;
	private boolean mRegisteredSensor;
	private SensorManager mSensorManager;
    private static final float NS2S = 1.0f / 1000000000.0f;
//    private final float[] deltaRotationVector = new float[4]();
    private float rx = 0;
	private float ry = 0;
	private float rz = 0;
	private float timestamp;
	
	public X_Sensor(Context context){
		this.context = context;
	}
	
	public void Gyro_onCreate() {
		mRegisteredSensor = false;
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	protected void Gyro_onResume() {
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
		if (sensors.size() > 0) {
			Sensor sensor = (Sensor) sensors.get(0);
			mRegisteredSensor = mSensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	protected void Gyro_onPause() {
		if (mRegisteredSensor) {
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
	}


	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		// This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];
            rx += axisX*dT;
            System.out.println("-------------------"+rx);
            ry += axisY*dT;
            System.out.println("-------------------"+ry);
            rz += axisZ*dT;
            System.out.println("-------------------"+rz);
            // Calculate the angular speed of the sample
            //float omegaMagnitude = sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            //if (omegaMagnitude > EPSILON) {
//                axisX /= omegaMagnitude;
//                axisY /= omegaMagnitude;
//                axisZ /= omegaMagnitude;
            //}

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
//            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
//            float sinThetaOverTwo = sin(thetaOverTwo);
//            float cosThetaOverTwo = cos(thetaOverTwo);
//            deltaRotationVector[0] = sinThetaOverTwo * axisX;
//            deltaRotationVector[1] = sinThetaOverTwo * axisY;
//            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
//            deltaRotationVector[3] = cosThetaOverTwo;
            
        }
        timestamp = event.timestamp;
//        float[] deltaRotationMatrix = new float[9];
//        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;
   }
}
