package multi.level.mlm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class Withdrawal extends AppCompatActivity {
    private RecyclerView recyclerView;
    String url="http://hsoftech.in/Mcq/MobileApi/getwithdra.php";

    String url_add="http://hsoftech.in/Mcq/MobileApi/addwithdra.php";
    TextView adddepo;
    String ckpath="";
    ImageView ckimg;
    String uid="";
    String bal="";
    // TextView txtdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        setTitle("Withdrawal List");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e)
        {

        }
        uid=SaveSharedPreference.getUserId(Withdrawal.this);
        bal=getIntent().getExtras().getString("bal");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_user1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Withdrawal.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adddepo=(TextView)findViewById(R.id.adddepo1);
        getuser();
        adddepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    double amt=Double.parseDouble(bal);
                    if(amt>=1000)
                    {
                        adddepo();
                    }else
                    {
                        Toast.makeText(Withdrawal.this, "Balance Amount not sufficient for withdrawal request..", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){}


            }
        });


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

    private void getuser() {
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
                                s.setDate(c.getString("dt"));
                                s.setAmount(c.getString("amt"));
                                s.setStatus(c.getString("status"));
                                s.setPer(c.getString("per"));
                                s.setInter_amt(c.getString("iamt"));
                                s.setTotal(c.getString("tamt"));

                               list1.add(s);



                            }
                            if(list1.size()==0)
                                findViewById(R.id.nijoin1).setVisibility(View.VISIBLE);


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
                params.put("uid",SaveSharedPreference.getUserId(Withdrawal.this));


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Withdrawal.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void adddepo()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);
                        if(response.trim().equalsIgnoreCase("month"))
                        {
                            Toast.makeText(Withdrawal.this, "Invalid Request(You can add request after 6 month of 1st Deposit/Last Withdrawal.)", Toast.LENGTH_SHORT).show();
                        }
                       else if(response.trim().equalsIgnoreCase("present"))
                        {
                            Toast.makeText(Withdrawal.this, "Request Already Added Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                       else if(response.trim().equalsIgnoreCase("yes"))
                        {
                           getuser();
                            Toast.makeText(Withdrawal.this, "Request Submitted !", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(Withdrawal.this, "Something went wrong try again !", Toast.LENGTH_SHORT).show();
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
                params.put("uid",uid);
                params.put("amt",bal);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Withdrawal.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

        private List<SetGetMethode> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView txtdt,txtsrno,txtamt,txtstatus,txttin,tper,tamt;


            public MyViewHolder(View view) {
                super(view);
                txtamt = (TextView) itemView.findViewById(R.id.txtwamt);
                txtsrno = (TextView) itemView.findViewById(R.id.txtsrno1);
                txtdt = (TextView) itemView.findViewById(R.id.txtdt1);
                txtstatus = (TextView) itemView.findViewById(R.id.txtstatus1);
                txttin = (TextView) itemView.findViewById(R.id.txttint);
                tper = (TextView) itemView.findViewById(R.id.txtintper);
                tamt = (TextView) itemView.findViewById(R.id.txttamt);

            }
        }


        public ItemAdapter(List<SetGetMethode> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.withdrawal, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final SetGetMethode movie = moviesList.get(position);
            holder.txtsrno.setText("#"+(position+1));
            holder.txtdt.setText("Date : "+movie.getDate());
            holder.txtamt.setText("AMOUNT : ₹ "+movie.getAmount());
            holder.tper.setText("Interest % : "+movie.getPer()+"%");
            holder.txttin.setText("Interest AMOUNT : ₹ "+movie.getInter_amt());
            holder.tamt.setText("Total Withdrawal AMOUNT : ₹ "+movie.getTotal());

            holder.txtstatus.setText(movie.getStatus());




        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }



}
