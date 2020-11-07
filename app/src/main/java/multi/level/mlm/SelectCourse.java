package multi.level.mlm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCourse extends AppCompatActivity {
    String str1="";
    private RecyclerView recyclerView;
    String url="http://hsoftech.in/Mcq/MobileApi/getCourseList.php";
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
         str1=getIntent().getExtras().getString("for");

        setTitle("Select Course");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backcolor)));
        } catch (Exception e)
        {

        }
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_course);

        recyclerView = (RecyclerView)findViewById(R.id.course_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectCourse.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        String str=getIntent().getExtras().getString("nm");
        //TextView txt=(TextView)findViewById(R.id.txttestnm);
       // txt.setText(str);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    if (new ConnectionDetector(SelectCourse.this).isConnectingToInternet()) {
                        getListt();
                    } else {
                        InternetError.showerro(SelectCourse.this);
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
                    if (new ConnectionDetector(SelectCourse.this).isConnectingToInternet()) {
                        getListt();
                    } else {
                        InternetError.showerro(SelectCourse.this);
                    }

                } catch (Exception e) {

                }
            }
        });

    }

    private void getListt() {
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
                        swipeRefreshLayout.setRefreshing(false);
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid",SaveSharedPreference.getUserId(SelectCourse.this));


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(SelectCourse.this);

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
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Intent intent= new Intent(SelectCourse.this,SelectSubject.class);
                        intent.putExtra("nm",movie.getName());
                        intent.putExtra("cid",movie.getId());
                        intent.putExtra("for",str1);
                        startActivity(intent);
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
