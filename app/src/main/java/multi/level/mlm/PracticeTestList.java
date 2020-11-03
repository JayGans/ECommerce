package multi.level.mlm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeTestList extends AppCompatActivity {
    private RecyclerView recyclerView;
String Chapter_id="";
double bal=0;
String uid="";

    String url="http://hsoftech.in/Mcq/MobileApi/getPractice_tests_List.php";
    String url_bal="http://hsoftech.in/Mcq/MobileApi/getprofile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);
        Chapter_id =getIntent().getExtras().getString("cid");
        setTitle("Select Paper");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backcolor)));
        } catch (Exception e)
        {

        }
uid=SaveSharedPreference.getUserId(PracticeTestList.this);
        TextView txtnm=(TextView)findViewById(R.id.csnm);
        recyclerView = (RecyclerView)findViewById(R.id.subject_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PracticeTestList.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
       String str=getIntent().getExtras().getString("nm");

getbal();
         txtnm.setText(str);

        getListt(Chapter_id);

    }

    private void getListt(final String cid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            double ertt=0;
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("data");
                            //  Toast.makeText(Withdrawal.this, ""+heroArray, Toast.LENGTH_SHORT).show();
                            List<SetGetMethode> list1 = new ArrayList<>();
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject c = heroArray.getJSONObject(i);


                                SetGetMethode s= new SetGetMethode();
                                s.setId(c.getString("id"));
                                s.setName(c.getString("nm"));
                                s.setFees(c.getString("fee"));
                                s.setNoQ(c.getString("no"));


                                list1.add(s);



                            }
                            recyclerView.setAdapter(new ItemAdapter(list1));




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
                params.put("cid",cid);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(PracticeTestList.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {


        finish();



    }


    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private List<SetGetMethode> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView txtnm,txtno;
            LinearLayout lay;


            public MyViewHolder(View view) {
                super(view);
                txtnm = (TextView) itemView.findViewById(R.id.txt_testnm);
                txtno = (TextView) itemView.findViewById(R.id.txt_qno);
                lay=(LinearLayout) itemView.findViewById(R.id.laytest);


            }
        }


        public ItemAdapter(List<SetGetMethode> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.test_adapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final SetGetMethode movie = moviesList.get(position);

            holder.txtnm.setText(movie.getName());
            holder.txtno.setText("Que:"+movie.getNoQ());
           // Drawable img = getResources().getDrawable(R.drawable.ic_sunny);
            Drawable img1 = getResources().getDrawable(R.drawable.ic_arrow_white);
           // img.setBounds(0, 0, 40, 40);
            img1.setBounds(0, 0, 50, 50);
           // holder.txtnm.setCompoundDrawables(null, null, img1, null);
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    try{
                           if(bal>0) {
                               Intent intent = new Intent(PracticeTestList.this, PracticeTest.class);

                               intent.putExtra("nm", movie.getName());
                               intent.putExtra("cid", Chapter_id);
                               intent.putExtra("tid", movie.getId());
                               intent.putExtra("for", "Practice Test");
                               startActivity(intent);
                           }
                           else
                           {
                               try{
                                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PracticeTestList.this);
                                   alertDialogBuilder.setMessage("Sorry No Sufficient Points Available.! \nPlease Add Points in your wallet.");


                                   alertDialogBuilder.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
                                       //Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           dialog.dismiss();
                                       }
                                   });
                                   alertDialogBuilder.setNegativeButton("Add Points",new DialogInterface.OnClickListener() {
                                       //Override
                                       public void onClick(DialogInterface dialog, int which) {

                                       }
                                   });

                                   AlertDialog alertDialog = alertDialogBuilder.create();
                                   alertDialog.show();
                               }catch (Exception e){}
                           }
                    }catch (Exception e){}

                }
            });




        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }

    public void getbal() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_bal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print("data" + response);
                        //  Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("profile");
                            // ArrayList<SetGetMethode> result = new ArrayList<>();

                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject c = heroArray.getJSONObject(i);

                                try {
                                   bal=Double.parseDouble(c.getString("points"));
                                }catch (Exception e){}


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",uid);
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(PracticeTestList.this);
        // pDialog.show();
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}