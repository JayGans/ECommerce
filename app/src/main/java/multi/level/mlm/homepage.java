package multi.level.mlm;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class homepage extends Fragment
{

    View rootView;
String bal="",twoper="0",oneper="0",halfper="0",earnbal="0";
    CardView deposit,earn,withdrawal;

      TextView txtbal,txtearn,txtdepo,txtwith,txtearnbal,txtearnwith;

    TextView txtref;
    String url="https://myapparelhub.com/Mcq/MobileApi/getbal.php";

    private SwipeRefreshLayout swipeRefreshLayout;
    public homepage()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_homepage, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipe_refresh);

        deposit=(CardView) rootView.findViewById(R.id.carddepo);
        //withdrawal=(CardView) rootView.findViewById(R.id.cardwith);

        txtbal=(TextView)rootView.findViewById(R.id.crbal);
rootView.findViewById(R.id.laylevel1).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try{
            Intent intent= new Intent(getActivity(),SelectCourse.class);
            intent.putExtra("nm","Learn Through Video");
            intent.putExtra("for","video");
            startActivity(intent);
        }catch (Exception e){}
    }
});
rootView.findViewById(R.id.laylevel2).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
try{
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.test_select, null);
    dialogBuilder.setView(dialogView);
    final AlertDialog b = dialogBuilder.create();

    // reference the textview of custom_popup_dialog
    LinearLayout practice = (LinearLayout) dialogView.findViewById(R.id.layptest);
    LinearLayout allr = (LinearLayout) dialogView.findViewById(R.id.layallr);






    practice.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                Intent intent= new Intent(getActivity(),SelectCourse.class);
                intent.putExtra("nm","Practice Test");
                intent.putExtra("for","Practice Test");
                startActivity(intent);
                b.dismiss();
            }catch (Exception e){}
        }
    });
    allr.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                Intent intent= new Intent(getActivity(),SelectCourse.class);
                intent.putExtra("nm","All India Rank Level");
                intent.putExtra("for","All India Rank Level");
                startActivity(intent);
                b.dismiss();
            }catch (Exception e){}
        }
    });


    b.show();
}catch (Exception e){}

    }
});




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                      //  getbal();
                    } else {
                        InternetError.showerro(getActivity());
                    }

                } catch (Exception e) {

                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);

                try {
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                       // getbal();
                    } else {
                        InternetError.showerro(getActivity());
                    }

                } catch (Exception e) {

                }
            }
        });



        return  rootView;
    }


    private void getbal() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                                JSONArray heroArray = obj.getJSONArray("data");
                            //  Toast.makeText(LevelDetails.this, ""+heroArray, Toast.LENGTH_SHORT).show();
                             for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject c = heroArray.getJSONObject(i);
                                 bal=c.getString("bal");
                                txtbal.setText("₹ "+c.getString("bal"));
                                 txtearn.setText("₹ "+c.getString("earn"));
                                 txtearnbal.setText("₹ "+c.getString("earnbal"));
                                 txtdepo.setText("₹ "+c.getString("deposit"));
                                 txtwith.setText("₹ "+c.getString("with"));
                                 txtearnwith.setText("₹ "+c.getString("earnwith"));
                                 twoper=c.getString("two");
                                 oneper=c.getString("one");
                                 halfper=c.getString("half");
                                 earnbal=c.getString("earnbal");

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
                        swipeRefreshLayout.setRefreshing(false);
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid",SaveSharedPreference.getUserId(getActivity()));


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void Reflink() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "GRAMVIKAS MULTISERVICES APP");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=gram.vikas.financeapp&hl=en \n\n";
            sAux = sAux + "use Refer code : " + SaveSharedPreference.getRefCode(getActivity()) + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }






}
