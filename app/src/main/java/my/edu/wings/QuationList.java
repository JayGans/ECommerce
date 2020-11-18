package my.edu.wings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuationList extends Activity {
    TextView _tv;
    TextView txtque;
    TextToSpeech t1;
    String Chapter_Id="",Test_id="";
    ProgressBar progressBar;
    CardView card_a,card_b,card_c,card_d;
    RelativeLayout rel_a,rel_b,rel_c,rel_d;
    String join_id="";
    int total_q=2,correct=0,wrong=0,earn=0,loss=0;
    String url_join="http://hsoftech.in/Mcq/MobileApi/join_rank_test.php";

    String url="http://hsoftech.in/Mcq/MobileApi/get_rank_questions_chapterwise.php";
    String url_add="http://hsoftech.in/Mcq/MobileApi/add_rank_questions_ans.php";
    CountDownTimer TimerCount=null;
    TextView opt1,opt2,opt3,opt4,q_count,txtright,txtwrong,txtearn,txtloss;
    ProgressBar progress_right,progress_wrong;
    ProgressBar p_a,p_b,p_c,p_d;
    TextView txt_a,txt_b,txt_c,txt_d;

    int q=0,skip=0;
    String [] qlist;
    String [] options;
    String [] ans;
    String [] per;
    String  [] qid;
    TextView txtskip;
    String uid="";
    TextView txtbal;
    TextView txttitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quation_list);
        uid=SaveSharedPreference.getUserId(QuationList.this);
        Chapter_Id=getIntent().getExtras().getString("cid");

        Test_id=getIntent().getExtras().getString("tid");
        jointest(Chapter_Id,Test_id);

        txttitle=(TextView)findViewById(R.id.txt_title3);
        txttitle.setText("All India Rank Level");
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        _tv = (TextView) findViewById( R.id.txtProgress3);
        rel_a=(RelativeLayout)findViewById(R.id.rel_a3);
        rel_b=(RelativeLayout)findViewById(R.id.rel_b3);
        rel_c=(RelativeLayout)findViewById(R.id.rel_c3);
        rel_d=(RelativeLayout)findViewById(R.id.rel_d3);
        txtright=(TextView)findViewById(R.id.txtright3);

        txtearn=(TextView)findViewById(R.id.txtearn3);
        txtloss=(TextView)findViewById(R.id.txtloss3);

        txt_a=(TextView)findViewById(R.id.txt_a3);
        txt_b=(TextView)findViewById(R.id.txt_b3);
        txt_c=(TextView)findViewById(R.id.txt_c3);
        txt_d=(TextView)findViewById(R.id.txt_d3);
        txtskip=(TextView)findViewById(R.id.practice_skipp3);

        p_a=(ProgressBar) findViewById(R.id.pro_a3);
        p_b=(ProgressBar)findViewById(R.id.pro_b3);
        p_c=(ProgressBar)findViewById(R.id.pro_c3);
        p_d=(ProgressBar)findViewById(R.id.pro_d3);


        txtwrong=(TextView)findViewById(R.id.txtwrong3);
        progress_right=(ProgressBar) findViewById(R.id.progress_right3);
        progress_wrong=(ProgressBar) findViewById(R.id.progress_wrong3);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        txtque=(TextView)findViewById(R.id.txt_queation3);

        opt1=(TextView)findViewById(R.id.opt13);
        opt2=(TextView)findViewById(R.id.opt23);
        opt3=(TextView)findViewById(R.id.opt33);
        opt4=(TextView)findViewById(R.id.opt43);
        q_count=(TextView)findViewById(R.id.q_count3);
        q_count.setText("1/2");

        findViewById(R.id.btvoice3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String toSpeak = txtque.getText().toString();
                    // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }catch (Exception e){}
            }
        });


        card_a=(CardView)findViewById(R.id.card_a3);
        card_b=(CardView)findViewById(R.id.card_b3);
        card_c=(CardView)findViewById(R.id.card_c3);
        card_d=(CardView)findViewById(R.id.card_d3);
        findViewById(R.id.bt50501).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                    {
                        card_b.setVisibility(View.INVISIBLE);
                        card_c.setVisibility(View.INVISIBLE);
                    }else if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                    {
                        card_a.setVisibility(View.INVISIBLE);
                        card_d.setVisibility(View.INVISIBLE);
                    }
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                    {
                        card_b.setVisibility(View.INVISIBLE);
                        card_d.setVisibility(View.INVISIBLE);
                    }
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                    {
                        card_c.setVisibility(View.INVISIBLE);
                        card_b.setVisibility(View.INVISIBLE);
                    }

                }catch (Exception e){}
            }
        });
        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                skip++;
                loss=loss-5;
                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                addans(Chapter_Id,qid[q],"","skip");
                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("earn",""+earn);

                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                    try{ TimerCount.cancel();}catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    try{ TimerCount.cancel();}catch (Exception e){}
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                }

            }
        });
        card_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                String ansis="wrong";
                if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    correct++;
                    txtright.setText(""+correct);
                    progress_right.setProgress(correct*10);
                    earn++;
                    txtearn.setText(""+earn);
                    ansis="correct";
                }else
                {
                    loss--;
                    txtloss.setText(""+loss);
                    wrong++;
                    txtwrong.setText(""+wrong);
                    progress_wrong.setProgress(wrong*10);
                    card_a.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }
                addans(Chapter_Id,qid[q],opt1.getText().toString(),ansis);
                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("earn",""+earn);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                    try{ TimerCount.cancel();}catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    try{ TimerCount.cancel();}catch (Exception e){}
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                }
            }
        });
        findViewById(R.id.btback3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuationList.this);
                    alertDialogBuilder.setMessage("Do you want to exit?");


                    alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
                        //Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                        //Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            try{ TimerCount.cancel();}catch (Exception e){}
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }catch (Exception e){}
            }
        });
        card_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                String ansis="wrong";
                if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    correct++;
                    txtright.setText(""+correct);
                    progress_right.setProgress(correct*10);
                    earn++;
                    txtearn.setText(""+earn);
                    ansis="correct";
                }else
                {
                    loss--;
                    txtloss.setText(""+loss);
                    wrong++;
                    txtwrong.setText(""+wrong);
                    progress_wrong.setProgress(wrong*10);
                    card_b.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));



                }
                addans(Chapter_Id,qid[q],opt2.getText().toString(),ansis);
                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("earn",""+earn);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                    try{ TimerCount.cancel();}catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    try{ TimerCount.cancel();}catch (Exception e){}
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        card_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                String ansis="wrong";
                if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    correct++;
                    txtright.setText(""+correct);
                    progress_right.setProgress(correct*10);
                    earn++;
                    txtearn.setText(""+earn);
                    ansis="correct";
                }else
                {
                    loss--;
                    txtloss.setText(""+loss);
                    wrong++;
                    txtwrong.setText(""+wrong);
                    progress_wrong.setProgress(wrong*10);
                    card_c.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }
                addans(Chapter_Id,qid[q],opt3.getText().toString(),ansis);
                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("earn",""+earn);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                    try{ TimerCount.cancel();}catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    try{ TimerCount.cancel();}catch (Exception e){}
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        card_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                String ansis="wrong";
                if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    correct++;
                    txtright.setText(""+correct);
                    progress_right.setProgress(correct*10);
                    earn++;
                    txtearn.setText(""+earn);
                    ansis="correct";
                }else
                {
                    loss--;
                    txtloss.setText(""+loss);
                    wrong++;
                    txtwrong.setText(""+wrong);
                    progress_wrong.setProgress(wrong*10);
                    card_d.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }
                addans(Chapter_Id,qid[q],opt4.getText().toString(),ansis);
                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("earn",""+earn);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                    try{ TimerCount.cancel();}catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    try{ TimerCount.cancel();}catch (Exception e){}
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        findViewById(R.id.btshowpeople3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_a.setVisibility(View.VISIBLE);
                rel_b.setVisibility(View.VISIBLE);
                rel_d.setVisibility(View.VISIBLE);
                rel_c.setVisibility(View.VISIBLE);
            }
        });

        getList(Chapter_Id,Test_id);

    }


    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        try{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuationList.this);
            alertDialogBuilder.setMessage("Do you want to exit?");


            alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
                //Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                //Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    try{ TimerCount.cancel();}catch (Exception e){}
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }catch (Exception e){}


    }
    public void starttimer()
    {
        card_a.setVisibility(View.VISIBLE);
        card_b.setVisibility(View.VISIBLE);
        card_c.setVisibility(View.VISIBLE);
        card_d.setVisibility(View.VISIBLE);

        txtque.setText(qlist[q]);
        String opt=options[q];
        String [] str=opt.split("~");
        opt1.setText(str[0]);
        opt2.setText(str[1]);
        opt3.setText(str[2]);
        opt4.setText(str[3]);

        String ps=per[q];
        String [] str_p=ps.split("~");
        txt_a.setText(str_p[0]+"%");
        txt_b.setText(str_p[1]+"%");
        txt_c.setText(str_p[2]+"%");
        txt_d.setText(str_p[3]+"%");

        p_a.setProgress(Integer.parseInt(str_p[0]));
        p_b.setProgress(Integer.parseInt(str_p[1]));
        p_c.setProgress(Integer.parseInt(str_p[2]));
        p_d.setProgress(Integer.parseInt(str_p[3]));

        TimerCount= new CountDownTimer(100000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                _tv.setText(""+millisUntilFinished / 1000);
                int p= (int) (millisUntilFinished / 1000);
                progressBar.setProgress(p);
            }

            public void onFinish() {

                if(q+1==total_q) {
                    try{
                        Intent intent= new Intent(QuationList.this,FinishAllRankExam.class);
                        intent.putExtra("yes",""+correct);
                        intent.putExtra("no",""+wrong);
                        intent.putExtra("skip",""+skip);
                        intent.putExtra("join",""+join_id);
                        intent.putExtra("loss",""+loss);
                        intent.putExtra("earn",""+earn);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){}
                }else
                {
                    q++;
                    q_count.setText((q+1)+"/2");
                    starttimer();
                    progressBar.setProgress(100);
                    card_a.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_b.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    card_d.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }

            }
        }.start();
    }





    private void getList(final String cid,final String testid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);


                            JSONArray heroArray = obj.getJSONArray("data");
                            //  Toast.makeText(Withdrawal.this, ""+heroArray, Toast.LENGTH_SHORT).show();
                            qid=new String[heroArray.length()];
                            qlist=new String[heroArray.length()];
                            options=new String[heroArray.length()];
                            ans=new String[heroArray.length()];
                            per=new String[heroArray.length()];
                            for (int i = 0; i < heroArray.length(); i++)
                            {
                                //getting the json object of the particular index inside the array
                                JSONObject c = heroArray.getJSONObject(i);

                                qid[i]= c.getString("id");
                                qlist[i]= c.getString("nm");
                                options[i]= c.getString("a")+"~"+c.getString("b")+"~"+c.getString("c")+"~"+c.getString("d");
                                ans[i]= c.getString("ans");
                                per[i]=  c.getString("pa")+"~"+c.getString("pb")+"~"+c.getString("pc")+"~"+c.getString("pd");


                            }
                            if(heroArray.length()>0)
                                starttimer();

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
                params.put("tid",testid);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(QuationList.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    void addans(final String cid,final String qid,final  String ans,final String ansis) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {


                        } catch (Exception e) {
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
                params.put("uid",SaveSharedPreference.getUserId(QuationList.this));
                params.put("tid",Test_id);
                params.put("join_id",join_id);
                params.put("cid",cid);
                params.put("qid",qid);
                params.put("ans",ans);
                params.put("what",ansis);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(QuationList.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    void jointest(final String cid,final String tid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_join,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);


                        try {
                            if(response.trim().equalsIgnoreCase("no") || response.trim().isEmpty())
                            {
                                Toast.makeText(QuationList.this, "Something went wrong please try again.!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else
                            {
                                join_id=response;

                            }

                        } catch (Exception e) {
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
                params.put("uid",SaveSharedPreference.getUserId(QuationList.this));
                params.put("cid",cid);
                params.put("tid",tid);



                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(QuationList.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}
