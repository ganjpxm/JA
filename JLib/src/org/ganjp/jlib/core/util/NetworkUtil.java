/**
 * NetworkUtil.java
 * 
 * Created by Gan Jianping on 12/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jlib.core.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * <p>Network Utility</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class NetworkUtil {
	/**
	 * <p>Is Network Available</p>
	 * 
	 * @param mActivity
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity mActivity) {  
	    Context context = mActivity.getApplicationContext(); 
	    ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
	    if (connectivity == null) {     
	      return false; 
	    } else {   
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();     
	        if (info != null) {         
	            for (int i = 0; i < info.length; i++) {            
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {               
	                    return true;  
	                }         
	            }      
	        }  
	    }    
	    return false; 
	} 
	
	/**
	 * <p>Is Wifi Available</p>
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isWifiAvailable(Context ctx) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {     
			      return false; 
		    } else {   
		    	NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);     
		    	if (wifiInfo!=null && wifiInfo.isConnected()) {
					return true;
				}
		    }   
		} catch(Exception e) {
			e.printStackTrace();
		}
		Log.i("ConnectionUtil.isWifiAvailable", "NO wifi!");
		return false;
	}
   
	
	/**
	 * <p>Post to server</P>
	 * 
	 * @param postUrl
	 * @param urlParameter "name=" + URLEncoder.encode("Greg") + "&email=" + URLEncoder.encode("greg at code dot geek dot sh");
	 */
	public static String post(String postUrl, String urlParameter) {
		URL url = null;
        try {
            url = new URL(postUrl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpURLConnection urlConn = null;
        try {
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        urlConn.setDoInput (true);// Let the run-time system (RTS) know that we want input.
        urlConn.setDoOutput (true);// Let the RTS know that we want to do output.
        urlConn.setUseCaches (false);// No caching, we want the real thing.
        try {
            urlConn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            urlConn.connect();
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Specify the content type if needed. urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send the request data.
        try {
            output.writeBytes(urlParameter);
            output.flush();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get response data.
        String result = null;
        try {
            input = new DataInputStream (urlConn.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			result = reader.readLine();
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
	}
}
