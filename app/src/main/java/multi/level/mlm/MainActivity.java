package multi.level.mlm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    String url="http://hsoftech.in/Mcq/MobileApi/Login.php";
    EditText ednm;
    ShowHidePasswordEditText edpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#2F90E5"));
               // window.setNavigationBarColor(Color.parseColor("#c10702"));
            }
        }catch (Exception e){}

        findViewById(R.id.btfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

       findViewById(R.id.btfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                       startActivity(new Intent(MainActivity.this,Registration.class));
                       finish();
            }
        });
      findViewById(R.id.btcrlog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login.class));

            }
        });

       /* findViewById(R.id.txtforpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                    Forgetpass();
                }else InternetError.showerro(MainActivity.this);
            }
        });*/

        String uss=SaveSharedPreference.getUserId(MainActivity.this);
        if(!uss.isEmpty())
        {

            if(new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                startActivity(new Intent(MainActivity.this,Home_Menu.class));
                finish();
            }
            else InternetError.showerro(MainActivity.this);
        }

        /*ednm=(EditText)findViewById(R.id.edusnm);
        edpass=(ShowHidePasswordEditText)findViewById(R.id.edpass);

        findViewById(R.id.btlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mno=ednm.getText().toString();
                String pass=edpass.getText().toString();
                if(!mno.isEmpty() && !pass.isEmpty())
                {
                    if(new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                        loaddataList(mno,pass);
                    }else InternetError.showerro(MainActivity.this);
                }else
                {
                   // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(view, "Enter User Name And  Password .", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
*/
    }
    private void loaddataList(final String mno,final String pass) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(MainActivity.this);
        // pDialog.setMessage("loading data...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print(""+response);
                        //  Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("login");
                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject c = heroArray.getJSONObject(i);
                                String str=c.getString("result");

                                if(str.equalsIgnoreCase("process"))
                                {
                                    //SendRequest();
                                }
                              else  if(str.equals("yes"))
                                {
                                    SaveSharedPreference.setUserId(MainActivity.this,c.getString("id"));
                                    SaveSharedPreference.setUserName(MainActivity.this,c.getString("name"));
                                    SaveSharedPreference.setRefCode(MainActivity.this,c.getString("ref"));

                                    Intent intent=new Intent(MainActivity.this,Home_Menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "User Not Exit !", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", mno);
                params.put("pass", pass);

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        pDialog.show();
        //adding the string request to request queue
        requestQueue.add(stringRequest);
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


   /* private void sendemail(final String email) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(MainActivity.this);
        // pDialog.setMessage("loading data...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);
                        // swipeRefreshLayout.setRefreshing(false);
                        pDialog.dismiss();
                        //  Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.trim().equals("no"))
                        {
                            Toast.makeText(MainActivity.this, "Email not exist !", Toast.LENGTH_SHORT).show();

                        }else if(response.trim().equals("yes"))
                        {

                            Toast.makeText(MainActivity.this, "Check Your Email Box", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "something went wrong try again !", Toast.LENGTH_LONG).show();

                        }


                        // Toast.makeText(Registration.this, ""+response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
             //   params.put("type", type);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        pDialog.show();
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
*/
    /*public void SendRequest()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.request, null);
        dialogBuilder.setView(dialogView);



        Button btadd = (Button) dialogView.findViewById(R.id.btsendreq);
        btadd.setText("OK");
        Button btcancel = (Button) dialogView.findViewById(R.id.btclodereq);
        btcancel.setVisibility(View.GONE);
        TextView title = (TextView) dialogView.findViewById(R.id.req_title);
        TextView info = (TextView) dialogView.findViewById(R.id.req_info);

        title.setText("ACCOUNT ACTIVATION");

        info.setText("Sorry your account activation payment still uncompleted.Please transfer Rs.500 on given bank account details for account activation.\n\nIf you have made payments already please wait your profile will be activate soon..\n\nThank you.");

        //dialogBuilder.setTitle(" Information");
        final AlertDialog b = dialogBuilder.create();


      *//*  btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });*//*


        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                  *//*  startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();*//*
                    b.dismiss();
                }catch (Exception e){}


            }
        });
        b.show();
    }*/
}
