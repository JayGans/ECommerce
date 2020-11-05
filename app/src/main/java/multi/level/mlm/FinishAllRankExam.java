package multi.level.mlm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FinishAllRankExam extends Activity {

    TextView txttotalearn,txttscor,txtcorrect,txtwrong,txtearn,txtloss,txtfper;
    ProgressBar probar;
    String url_add="http://hsoftech.in/Mcq/MobileApi/finish_rank_test.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_all_rank_exam);



        String correct=getIntent().getExtras().getString("yes");
        String wrong=getIntent().getExtras().getString("no");
        String skip=getIntent().getExtras().getString("skip");
        String joinid=getIntent().getExtras().getString("join");
        String earn=getIntent().getExtras().getString("earn");

        txtfper=(TextView)findViewById(R.id.txtProgress_f1);
        probar=(ProgressBar) findViewById(R.id.progressBar_f1);
        try{

            int ts=Integer.parseInt(correct)+Integer.parseInt(wrong)+Integer.parseInt(skip);
            Double percentage = ((double)Integer.parseInt(correct)/ts) * 100;
            txtfper.setText(Math.round(percentage)+"%");

            probar.setProgress(Integer.valueOf(percentage.intValue()));

        }catch(Exception e){}
        txttotalearn=(TextView)findViewById(R.id.txttotalearn1);
       // txttotalearn.setText(""+earn);
        txttscor=(TextView)findViewById(R.id.txt_total_scr1);
        txttscor.setText(""+correct);

        txtcorrect=(TextView)findViewById(R.id.txt_correct1);
        txtcorrect.setText(""+correct);


        txtwrong=(TextView)findViewById(R.id.txt_wrong1);
        txtwrong.setText(""+wrong);



        txtloss=(TextView)findViewById(R.id.txt_loss1);
        txtloss.setText(""+skip);
        getrank(joinid,correct,wrong);
        findViewById(R.id.playgain1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("MESSAGE","load");
                setResult(2,intent);
                finish();//finishing activity
            }
        });

        findViewById(R.id.btback11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("MESSAGE","load");
                setResult(2,intent);
                finish();//finishing activity
            }
        });



        findViewById(R.id.gohome1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent= new Intent(FinishAllRankExam.this,Home_Menu.class);
                    startActivity(intent);
                }catch (Exception e){}
            }
        });


    }

    void getrank(final String jid,final String correct,final  String wrong) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(FinishAllRankExam.this);
        pDialog.setMessage("Wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //  progressBar.setVisibility(View.INVISIBLE);
                        pDialog.dismiss();

                        try {
if(response.trim().isEmpty() || !response.equalsIgnoreCase("no"))
                            txttotalearn.setText(""+response);


                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        pDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("jid",jid);

                params.put("c",correct);
                params.put("w",wrong);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(FinishAllRankExam.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}