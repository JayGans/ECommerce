package multi.level.mlm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewards extends Fragment {

    String url="http://hsoftech.in/Mcq/MobileApi/getleveldetails.php";
    View rootView;
    public MyRewards()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_my_rewards, container, false);
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
            loaddata();
        else InternetError.showerro(getActivity());
        return  rootView;
    }
    private void loaddata()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("level");
                            System.out.println("home**********"+heroArray);

                            for(int i = 0; i <heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject c = heroArray.getJSONObject(i);


                                // SetGetMethode s= new SetGetMethode();
                                //s.setName(c.getString("nm"));
                                //s.setMno(c.getString("mno"));
                                try
                                {
                                    if (c.getString("1status").equalsIgnoreCase("Completed")) {
                                        rootView.findViewById(R.id.rew1).setVisibility(View.VISIBLE);
                                    }

                                    if (c.getString("2status").equalsIgnoreCase("Completed")) {
                                        rootView.findViewById(R.id.rew2).setVisibility(View.VISIBLE);
                                    }
                                    if (c.getString("3status").equalsIgnoreCase("Completed")) {
                                        rootView.findViewById(R.id.rew3).setVisibility(View.VISIBLE);
                                    }

                                    if (c.getString("4status").equalsIgnoreCase("Completed")) {
                                        rootView.findViewById(R.id.rew4).setVisibility(View.VISIBLE);
                                    }
                                    if (c.getString("5status").equalsIgnoreCase("Completed")) {
                                        rootView.findViewById(R.id.rew5).setVisibility(View.VISIBLE);
                                    }
                                }catch (Exception e){
                                    Toast.makeText(getActivity(), "Load activity again !", Toast.LENGTH_SHORT).show();
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
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid",SaveSharedPreference.getUserId(getActivity()));
                params.put("ref",SaveSharedPreference.getRefCode(getActivity()));

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
