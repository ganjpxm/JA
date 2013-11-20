package org.ganjp.jone.sample.thirdparty;

import java.util.Arrays;

import org.ganjp.jone.R;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class FacebookFragment extends Fragment {  
    private static final String TAG = "FacebookFragment";  
    private UiLifecycleHelper uiHelper;  
    private LoginButton LoginBtn;
    private Button queryBtn;  
    private Button multiQueryBtn;  
    private ImageView mPhotoIv;
    private TextView mProfileTv;
  
    private Session.StatusCallback callback = new Session.StatusCallback() {  
        @Override  
        public void call(Session session, SessionState state, Exception exception) {  
            onSessionStateChange(session, state, exception);  
        }  
    };  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        uiHelper = new UiLifecycleHelper(getActivity(), callback);  
        uiHelper.onCreate(savedInstanceState);  
    }  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.sample_facebook_fragment, container, false);  
  
        LoginBtn = (LoginButton) view.findViewById(R.id.login_button);  
        LoginBtn.setFragment(this);  
        LoginBtn.setReadPermissions(Arrays.asList("email","user_likes", "user_status"));  
          
        queryBtn = (Button) view.findViewById(R.id.queryButton);  
        multiQueryBtn = (Button) view.findViewById(R.id.multiQueryButton);  
  
        mPhotoIv = (ImageView) view.findViewById(R.id.photo_iv);
        mProfileTv = (TextView) view.findViewById(R.id.profile_tv);
        /* 
         * { 
                data: [ 
                    { 
                        pic_square: "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/41666_1424840234_9458_q.jpg", 
                        uid: 1424840234, 
                        name: "Christine Abernathy" 
                    }, 
                    { 
                        pic_square: "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash4/211464_100002650977863_2642718_q.jpg", 
                        uid: 100002650977863, 
                        name: "James Forton" 
                    } 
                ] 
            } 
         */  
        queryBtn.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                String fqlQuery = "SELECT uid, name, pic_square FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me() LIMIT 25)";  
                Bundle params = new Bundle();  
                params.putString("q", fqlQuery);  
                Session session = Session.getActiveSession();  
                Request request = new Request(session,  
                    "/fql",                           
                    params,                           
                    HttpMethod.GET,                   
                    new Request.Callback(){           
                        public void onCompleted(Response response) {  
                            try {  
                                GraphObject graphObject = response.getGraphObject();  
                                FacebookRequestError error = response.getError();  
                                if (graphObject != null) {  
                                    // Check if there is extra data  
                                    if (graphObject.getProperty("data") != null) {  
                                        JSONArray dataArray = new JSONArray(graphObject.getProperty("data").toString());  
                                        for(int i=0;i<=dataArray.length();i++){  
                                            JSONObject dataObject = (JSONObject)dataArray.get(i);  
                                              
                                            String pic = dataObject.getString("pic_square");  
                                            String uid = dataObject.getString("uid");  
                                            String name = dataObject.getString("name");  
                                            mProfileTv.setText(pic+"\n"+uid+"\n"+name); 
                                        }  
                                    }else if (error != null) {  
                                          
                                    }  
                                }  
                                Log.i(TAG, "Result: " + response.toString());  
                            } catch (Exception e) {  
                                e.printStackTrace();  
                            }  
                        }                    
                });   
                Request.executeBatchAsync(request);                   
            }  
        });  
          
          
        /* 
         { 
                data: [ 
                    { 
                    fql_result_set: [ 
                        { 
                            uid2: "1424840234" 
                        }, 
                        { 
                            uid2: "100002650977863" 
                        } 
                    ], 
                    name: "friends" 
                }, 
                { 
                    fql_result_set: [ 
                        { 
                            pic_square: "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/41666_1424840234_9458_q.jpg", 
                            uid: 1424840234, 
                            name: "Christine Abernathy" 
                        }, 
                        { 
                            pic_square: "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash4/211464_100002650977863_2642718_q.jpg", 
                            uid: 100002650977863, 
                            name: "James Forton" 
                        } 
                    ], 
                    name: "friendinfo" 
                } 
                ] 
            } 
         */  
        multiQueryBtn.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) { 
               /* String fqlQuery = "{" + 
                      "'friends':'SELECT uid2 FROM friend WHERE uid1 = me() LIMIT 25'," + 
                      "'friendinfo':'SELECT uid, name, pic_square FROM user WHERE uid IN " + 
                      "(SELECT uid2 FROM #friends)'," + 
                      "}";*/  
                String fqlQuery = "SELECT uid,name,email FROM user WHERE uid = me()";  
                Bundle params = new Bundle();  
                params.putString("q", fqlQuery);  
                Session session = Session.getActiveSession();  
                Request request = new Request(session,  
                    "/fql",                           
                    params,                           
                    HttpMethod.GET,                   
                    new Request.Callback(){           
                        public void onCompleted(Response response) {  
                        	mProfileTv.setText(response.toString());
                            Log.i(TAG, "Result: " + response.toString());  
                        }                    
                });   
                Request.executeBatchAsync(request);                   
            }  
        });  
  
        return view;  
    }  
  
    private void onSessionStateChange(Session session, SessionState state,  Exception exception) {  
        if (state.isOpened()) {  
//        	LoginBtn.setVisibility(View.VISIBLE);  
            queryBtn.setVisibility(View.VISIBLE);  
            multiQueryBtn.setVisibility(View.VISIBLE);  
            makeMeRequest(session);  
            Log.i(TAG, "Logged in...");  
        } else if (state.isClosed()) {  
//        	LoginBtn.setVisibility(View.INVISIBLE);  
            queryBtn.setVisibility(View.INVISIBLE);  
            multiQueryBtn.setVisibility(View.INVISIBLE);  
            mProfileTv.setText("");
            Log.i(TAG, "Logged out...");  
        }  
    }  
      
    /** 
     * 获取登录信息 
     * @param session 
     */  
    private void makeMeRequest(final Session session) {  
          
//      Bundle params1 = new Bundle();  
//      params1.putString("fields","picture");  
//      Log.v("sss", authenticatedFacebook.request("me"));  
//      JSONObject jObject = new JSONObject(authenticatedFacebook.request("me"));  
//      String id=jObject.getString("id");  
//      String name=jObject.getString("name");  
//      String email=jObject.getString("email");  
//      try {  
//         URL img_value = new URL("http://graph.facebook.com/"+id+"/picture?type=large");  
//         Bitmap mIcon1 =     BitmapFactory.decodeStream(img_value.openConnection().getInputStream());  
//      } catch (Exception e) {  
//          e.printStackTrace();  
//      }  
          
          
        // Make an API call to get user data and define a  
        // new callback to handle the response.  
        Request request = Request.newMeRequest(session,  
	        new Request.GraphUserCallback() {  
	            @Override  
	            public void onCompleted(GraphUser user, Response response) {  
	                // If the response is successful  
	                if (session == Session.getActiveSession()) {  
	                    if (user != null) {
	                        try {  
	                            Toast.makeText(FacebookFragment.this.getActivity(),user.getProperty("email")+"", Toast.LENGTH_LONG).show();  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }
	                    }  
	                }  
	                if (response.getError() != null) {  
	                    // Handle errors, will do so later.  
	                }  
	            }  
	    });  
        request.executeAsync();  
    }  
  
    @Override  
    public void onResume() {  
        super.onResume();  
  
        // For scenarios where the main activity is launched and user  
        // session is not null, the session state change notification  
        // may not be triggered. Trigger it if it's open/closed.  
        Session session = Session.getActiveSession();  
        if (session != null && (session.isOpened() || session.isClosed())) {  
            onSessionStateChange(session, session.getState(), null);  
        }  
  
        uiHelper.onResume();  
    }  
  
    @Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        uiHelper.onActivityResult(requestCode, resultCode, data);  
    }  
  
    @Override  
    public void onPause() {  
        super.onPause();  
        uiHelper.onPause();  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        uiHelper.onDestroy();  
    }  
  
    @Override  
    public void onSaveInstanceState(Bundle outState) {  
        super.onSaveInstanceState(outState);  
        uiHelper.onSaveInstanceState(outState);  
    }  
}  