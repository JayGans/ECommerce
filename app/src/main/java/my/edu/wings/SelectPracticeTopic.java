package my.edu.wings;

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

public class SelectPracticeTopic extends AppCompatActivity {
    private RecyclerView recyclerView;
String str1="";
    String sid="";
    private SwipeRefreshLayout swipeRefreshLayout;
    String url="http://hsoftech.in/Mcq/MobileApi/getTopicList.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);
         sid=getIntent().getExtras().getString("id");
        str1=getIntent().getExtras().getString("for");

        setTitle(str1);
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backcolor)));
        } catch (Exception e)
        {

        }
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_sub);

        TextView txtnm=(TextView)findViewById(R.id.csnm);
        recyclerView = (RecyclerView)findViewById(R.id.subject_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectPracticeTopic.this);
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
                    if (new ConnectionDetector(SelectPracticeTopic.this).isConnectingToInternet()) {
                        getListt(sid);
                    } else {
                        InternetError.showerro(SelectPracticeTopic.this);
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
                    if (new ConnectionDetector(SelectPracticeTopic.this).isConnectingToInternet()) {
                        getListt(sid);
                    } else {
                        InternetError.showerro(SelectPracticeTopic.this);
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

                        swipeRefreshLayout.setRefreshing(false);
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


                                list1.add(s);



                            }
                            recyclerView.setAdapter(new ItemAdapter(list1));




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


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SelectPracticeTopic.this);

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

            public TextView txtnm;
            LinearLayout lay;


            public MyViewHolder(View view) {
                super(view);
                txtnm = (TextView) itemView.findViewById(R.id.txtnm);
                lay=(LinearLayout) itemView.findViewById(R.id.laycourse);

            }
        }


        public ItemAdapter(List<SetGetMethode> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_adapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final SetGetMethode movie = moviesList.get(position);

            holder.txtnm.setText(movie.getName());
            Drawable img = getResources().getDrawable(R.drawable.ic_sunny);
            Drawable img1 = getResources().getDrawable(R.drawable.ic_arrow_white);
            img.setBounds(0, 0, 40, 40);
            img1.setBounds(0, 0, 50, 50);
            holder.txtnm.setCompoundDrawables(img, null, img1, null);
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{


                        try{
                            Intent intent=null;
                            if(str1.equalsIgnoreCase("Practice Test"))
                                intent= new Intent(SelectPracticeTopic.this,PracticeTestList.class);
                            else if(str1.equalsIgnoreCase("Learn Through Video"))
                                intent= new Intent(SelectPracticeTopic.this,SelectVideo.class);
                                else
                                intent= new Intent(SelectPracticeTopic.this,SelectChapter.class);

                            intent.putExtra("nm",movie.getName());
                            intent.putExtra("cid",movie.getId());
                            intent.putExtra("for",str1);
                            startActivity(intent);
                        }catch (Exception e){}


                    }catch (Exception e){}
                }
            });




        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }
}