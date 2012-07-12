package xmlcz.Remote_x;

import android.os.Bundle;

public class KeyAndMouse extends X_Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyandmouse);
        mSocket.sendData("keyandmouse");
    }
}