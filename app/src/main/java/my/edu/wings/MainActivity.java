package my.edu.wings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends Activity implements GoogleApiClient.OnConnectionFailedListener {
    String url="http://hsoftech.in/Mcq/MobileApi/Loginbysocial.php";
    EditText ednm;
    private static final int RC_SIGN_IN = 1;
    ShowHidePasswordEditText edpass;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String uss=SaveSharedPreference.getUserId(MainActivity.this);
        if(!uss.isEmpty())
        {

            if(new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                startActivity(new Intent(MainActivity.this,Home_Menu.class));
                finish();
            }
            else InternetError.showerro(MainActivity.this);
        }else {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(this)
                    //  .enableAutoManage(this,this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.parseColor("#2F90E5"));
                    // window.setNavigationBarColor(Color.parseColor("#c10702"));
                }
            } catch (Exception e) {
            }

            findViewById(R.id.btgoogle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                }
            });
            // boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
            findViewById(R.id.btfb).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, Registration.class));
                    finish();
                }
            });
            findViewById(R.id.btcrlog).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, Login.class));

                }
            });

            loginButton = findViewById(R.id.login_button);

       /* findViewById(R.id.txtforpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                    Forgetpass();
                }else InternetError.showerro(MainActivity.this);
            }
        });*/

            // FacebookSdk.sdkInitialize(getApplicationContext());
            //AppEventsLogger.activateApp(this);
            boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

            if (!loggedOut) {


                //Using Graph API
                getUserProfile(AccessToken.getCurrentAccessToken());
            }
            loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
            callbackManager = CallbackManager.Factory.create();

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    //loginResult.getAccessToken();
                    //loginResult.getRecentlyDeniedPermissions()
                    //loginResult.getRecentlyGrantedPermissions()
                    //  Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    //  boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                    //  Log.d("API123", loggedIn + " ??");
                    getUserProfile(AccessToken.getCurrentAccessToken());

                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });

        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "my.edu.wings",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("********KeyHash***************"+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }



    public void Forgetpass() {
        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.forgetpass, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog b = dialogBuilder.create();

        final EditText ednm=(EditText) dialogView.findViewById(R.id.edforemail);
        Button btsub=(Button)dialogView.findViewById(R.id.btforsubmit);
        Button btcancel=(Button)dialogView.findViewById(R.id.btforcancel);


        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });


        b.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
           // userName.setText(account.getDisplayName());
          //  userEmail.setText(account.getEmail());
           // userId.setText(account.getId());
            loaddataList(account.getDisplayName(),account.getEmail(),account.getId(),"gmail");
          //  Toast.makeText(this, ""+account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }
    private void loaddataList(final String nm,final String email,final String id,final String by) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print("data" + response);
                        //  Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {


                        pDialog.dismiss();}catch (Exception e){}

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("result");
                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject c = heroArray.getJSONObject(i);
                                String s=c.getString("res");
                                if (s.equals("yes")) {

                                    SaveSharedPreference.setUserId(MainActivity.this,c.getString("id"));
                                    SaveSharedPreference.setUserName(MainActivity.this,nm);

                                    Intent intent=new Intent(MainActivity.this,Home_Menu.class);
                                    startActivity(intent);
                                    finish();
                                } else if (s.equals("no")) {
                                    Toast.makeText(MainActivity.this, " try again !", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(MainActivity.this, "poor internet connection try again !", Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //  pDialog.dismiss();
                        pDialog.dismiss();
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nm", nm);

                params.put("email", email);
                params.put("id", id);

                params.put("by", by);
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        // pDialog.show();
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                           // Toast.makeText(MainActivity.this, ""+first_name, Toast.LENGTH_SHORT).show();
                            loaddataList(first_name+" "+last_name,email,id,"fb");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

      Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }
}

