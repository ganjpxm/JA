/**
 * SplashActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.util.ThreadUtil;
import org.ganjp.jone.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * <p>Splash screen activity</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class SplashActivity extends JpActivity {
	
    private boolean mIsBackButtonPressed; // used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_splash);
		
		// run a thread after 3 seconds to start the Loan Activity
		ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                // make sure we close the splash screen so the user won't come back when it presses back key
                finish();
                
                // start the Loan Activity if the back button wasn't pressed already 
                if (!mIsBackButtonPressed) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    SplashActivity.this.startActivity(intent);
                    transitForward();
                }
            }
         }, JpConst.DURATION_SPLASH);
	}
	
	@Override
	public void onBackPressed() {
		mIsBackButtonPressed = true; // set the flag to true so the next activity won't start up
		super.onBackPressed();
	}

}