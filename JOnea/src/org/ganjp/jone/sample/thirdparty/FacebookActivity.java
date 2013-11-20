package org.ganjp.jone.sample.thirdparty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

public class FacebookActivity extends FragmentActivity {  
  
    private FacebookFragment mainFragment;  
      
    @Override  
	protected void onCreate(Bundle savedInstanceState) {  
    	super.onCreate(savedInstanceState);  
        if (savedInstanceState == null) {  
            // Add the fragment on initial activity setup  
            mainFragment = new FacebookFragment();  
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();  
        } else {  
        	// Or set the fragment from restored state info  
        	mainFragment = (FacebookFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);  
        }  
           
        try {  
        	PackageInfo info = getPackageManager().getPackageInfo("org.ganjp.jone",  PackageManager.GET_SIGNATURES);  
            for (Signature signature : info.signatures) {  
                MessageDigest md = MessageDigest.getInstance("SHA");  
                md.update(signature.toByteArray());  
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));  
            }  
        } catch (NameNotFoundException e) {  
  
        } catch (NoSuchAlgorithmException e) {  
  
        }  
    }   
} 