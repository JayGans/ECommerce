package multi.level.mlm;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    String url="http://hsoftech.in/Mcq/MobileApi/getrankhistory.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Join Test History");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backcolor)));
        } catch (Exception e)
        {

        }
        recyclerView = (RecyclerView)findViewById(R.id.history_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(History.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        getListt();

    }

    private void getListt() {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(History.this);
        pDialog.setMessage("Wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);
pDialog.dismiss();

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
                               // s.setId(c.getString("id"));
                                s.setName(c.getString("nm"));
                                s.setNoQ(c.getString("no"));
                                s.setWrong(c.getString("w"));
                                s.setCorrect(c.getString("c"));
                                s.setRank(c.getString("rank"));
                                s.setDate(c.getString("dt"));

                                list1.add(s);



                            }
                            recyclerView.setAdapter(new ItemAdapter(list1));




                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
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
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid",SaveSharedPreference.getUserId(History.this));


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(History.this);

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

            public TextView txtnm,txtdt,txtrank,txtcorrect,txtwrong,txtnoofq;
            LinearLayout lay;


            public MyViewHolder(View view) {
                super(view);
                txtnm = (TextView) itemView.findViewById(R.id.paper_nm);
                txtdt = (TextView) itemView.findViewById(R.id.paper_dt);
                txtcorrect = (TextView) itemView.findViewById(R.id.paper_c);
                txtnoofq = (TextView) itemView.findViewById(R.id.paper_noq);
                txtrank = (TextView) itemView.findViewById(R.id.paper_rank);
                txtwrong = (TextView) itemView.findViewById(R.id.paper_w);
            }
        }


        public ItemAdapter(List<SetGetMethode> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_adapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final SetGetMethode movie = moviesList.get(position);

            holder.txtnm.setText(movie.getName());
            holder.txtwrong.setText("Wrong :"+movie.getWrong());
            holder.txtcorrect.setText("Correct :"+movie.getCorrect());
            holder.txtnoofq.setText("No Of Que :"+movie.getNoQ());
            holder.txtrank.setText("Rank No-#"+movie.getRank());
            holder.txtdt.setText("Date :"+movie.getDate());





        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }
}