package com.remoter.mobile;

import android.os.Bundle;

public class JoyStick extends X_Activity{

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joystick);
        mSocket.sendData("joystick");
    }

}
