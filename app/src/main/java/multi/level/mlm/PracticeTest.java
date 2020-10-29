package multi.level.mlm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PracticeTest extends Activity {
    TextView _tv;
    TextView txtque;
    TextToSpeech t1;
    ProgressBar progressBar;
    CardView card_a,card_b,card_c,card_d;
    RelativeLayout rel_a,rel_b,rel_c,rel_d;
    int total_q=2;
    CountDownTimer TimerCount=null;
    TextView opt1,opt2,opt3,opt4,q_count;
    int q=0;
    String [] qlist={"Is it possible to have an activity without UI to perform action/actions?","How many sizes are supported by Android?"};
    String [] options={"Not possible~Wrong question~Yes, it is possible~None of the above","Android supported all sizes~Android does not support all sizes~ Android supports small,normal, large and extra-large sizes~Size is undefined in android"};
   String [] ans={"Not possible","Android supported all sizes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_test);


        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        _tv = (TextView) findViewById( R.id.txtProgress );
        rel_a=(RelativeLayout)findViewById(R.id.rel_a);
        rel_b=(RelativeLayout)findViewById(R.id.rel_b);
        rel_c=(RelativeLayout)findViewById(R.id.rel_c);
        rel_d=(RelativeLayout)findViewById(R.id.rel_d);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        txtque=(TextView)findViewById(R.id.txt_queation);

        opt1=(TextView)findViewById(R.id.opt1);
        opt2=(TextView)findViewById(R.id.opt2);
        opt3=(TextView)findViewById(R.id.opt3);
        opt4=(TextView)findViewById(R.id.opt4);
        q_count=(TextView)findViewById(R.id.q_count);
        q_count.setText("1/2");

        findViewById(R.id.btvoice).setOnClickListener(new View.OnClickListener() {
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


        card_a=(CardView)findViewById(R.id.card_a);
        card_b=(CardView)findViewById(R.id.card_b);
        card_c=(CardView)findViewById(R.id.card_c);
        card_d=(CardView)findViewById(R.id.card_d);

        card_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                }else
                {
                    card_a.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }

                if(q+1==total_q) {
                    Toast.makeText(PracticeTest.this, "Exam Finish !", Toast.LENGTH_SHORT).show();
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

        card_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rel_a.setVisibility(View.GONE);
                rel_b.setVisibility(View.GONE);
                rel_d.setVisibility(View.GONE);
                rel_c.setVisibility(View.GONE);
                if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                }else
                {
                    card_b.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));



                }
                if(q+1==total_q) {
                    Toast.makeText(PracticeTest.this, "Exam Finish !", Toast.LENGTH_SHORT).show();
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
                if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                }else
                {
                    card_c.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                        card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }
                if(q+1==total_q) {
                    Toast.makeText(PracticeTest.this, "Exam Finish !", Toast.LENGTH_SHORT).show();
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
                if(opt4.getText().toString().equalsIgnoreCase(ans[q]))
                {
                    card_d.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                }else
                {
                    card_d.setCardBackgroundColor(Color.parseColor("#F44336"));
                    if(opt1.getText().toString().equalsIgnoreCase(ans[q]))
                        card_a.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt3.getText().toString().equalsIgnoreCase(ans[q]))
                        card_c.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));
                    else if(opt2.getText().toString().equalsIgnoreCase(ans[q]))
                        card_b.setCardBackgroundColor(Color.parseColor("#FF4CAF50"));


                }
                if(q+1==total_q) {
                    Toast.makeText(PracticeTest.this, "Exam Finish !", Toast.LENGTH_SHORT).show();
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
findViewById(R.id.btshowpeople).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        rel_a.setVisibility(View.VISIBLE);
        rel_b.setVisibility(View.VISIBLE);
        rel_d.setVisibility(View.VISIBLE);
        rel_c.setVisibility(View.VISIBLE);
    }
});


        starttimer();
    }
    public void starttimer()
    {
        txtque.setText(qlist[q]);
        String opt=options[q];
        String [] str=opt.split("~");
        opt1.setText(str[0]);
        opt2.setText(str[1]);
        opt3.setText(str[2]);
        opt4.setText(str[3]);

         TimerCount= new CountDownTimer(100000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                _tv.setText(""+millisUntilFinished / 1000);
                int p= (int) (millisUntilFinished / 1000);
                progressBar.setProgress(p);
            }

            public void onFinish() {

                if(q+1==total_q) {
                    Toast.makeText(PracticeTest.this, "Exam Finish !", Toast.LENGTH_SHORT).show();
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
}
