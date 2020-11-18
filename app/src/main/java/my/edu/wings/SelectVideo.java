package my.edu.wings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectVideo extends AppCompatActivity {
    private RecyclerView recyclerView;
    String Chapter_id="";
    private SwipeRefreshLayout swipeRefreshLayout;
    String uid="";

    String url="http://hsoftech.in/Mcq/MobileApi/get_video_List.php";
    String url_add="http://hsoftech.in/Mcq/MobileApi/activate_video_Link.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);
        Chapter_id =getIntent().getExtras().getString("cid");
        setTitle("Select Video");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backcolor)));
        } catch (Exception e)
        {

        }

        uid=SaveSharedPreference.getUserId(SelectVideo.this);
        TextView txtnm=(TextView)findViewById(R.id.csnm);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_sub);

        recyclerView = (RecyclerView)findViewById(R.id.subject_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectVideo.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        String str=getIntent().getExtras().getString("nm");

        txtnm.setText(str);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    if (new ConnectionDetector(SelectVideo.this).isConnectingToInternet()) {
                        getListt(Chapter_id);
                    } else {
                        InternetError.showerro(SelectVideo.this);
                    }

                } catch (Exception e) {

                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                try {
                    if (new ConnectionDetector(SelectVideo.this).isConnectingToInternet()) {
                        getListt(Chapter_id);
                    } else {
                        InternetError.showerro(SelectVideo.this);
                    }

                } catch (Exception e) {

                }
            }
        });

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
                                s.setVideoUrl(c.getString("video"));
                                if(c.getString("status").equalsIgnoreCase("yes"))
                                {
                                    s.setIcon(R.drawable.ic_lock_open);
                                    s.setStatus("yes");
                                }else
                                {

                                    s.setIcon(R.drawable.ic_lock_close);
                                    s.setStatus("no");
                                }


                                list1.add(s);



                            }
                            recyclerView.setAdapter(new ItemAdapter(list1));


                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
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
                params.put("cid",cid);
                params.put("uid",uid);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SelectVideo.this);

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

            public TextView txtnm,txtfee,txticon;
            LinearLayout lay;
            ImageView img;


            public MyViewHolder(View view) {
                super(view);
                txtnm = (TextView) itemView.findViewById(R.id.txt_video_tnm);

                txtfee = (TextView) itemView.findViewById(R.id.txt_video_fee);
                lay=(LinearLayout) itemView.findViewById(R.id.lay_video_q);
                img=(ImageView) itemView.findViewById(R.id.video_img);
                txticon = (TextView) itemView.findViewById(R.id.txt_video_status);



            }
        }


        public ItemAdapter(List<SetGetMethode> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_adapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final SetGetMethode movie = moviesList.get(position);

            holder.txtnm.setText(movie.getName());
            holder.txtfee.setText("₹"+movie.getFees());
            Drawable img = getResources().getDrawable(movie.getIcon());
            img.setBounds(0, 0, 60, 60);
            holder.txticon.setCompoundDrawables(img, null, null, null);
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    try {

                        if (movie.getStatus().equalsIgnoreCase("yes")) {
                            Intent intent = new Intent(SelectVideo.this, VideoList.class);

                            intent.putExtra("nm", movie.getName());
                            intent.putExtra("video", movie.getVideoUrl());
                            startActivityForResult(intent, 2);
                        } else {
                            try {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelectVideo.this);
                                alertDialogBuilder.setMessage("Activate this video link");


                                alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    //Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Activate", new DialogInterface.OnClickListener() {
                                    //Override
                                    public void onClick(DialogInterface dialog, int which) {
active(movie.getId(),movie.getVideoUrl(),movie.getName());
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            } catch (Exception e) {
                            }
                        }
                    }
                    catch (Exception e){}

                }
            });




        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
getListt(Chapter_id);
        }
    }

     void active(final String vid,final String videourl,final String nm) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {

                       if(response.trim().equalsIgnoreCase("yes"))
                       {
                           Intent intent = new Intent(SelectVideo.this, VideoList.class);

                           intent.putExtra("nm",nm);
                           intent.putExtra("video", videourl);
                           startActivityForResult(intent, 2);
                       }else
                       {
                           Toast.makeText(SelectVideo.this, "Network problem try Again!", Toast.LENGTH_SHORT).show();
                       }




                        } catch (Exception e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
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
                params.put("vid",vid);
                params.put("uid",uid);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SelectVideo.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}