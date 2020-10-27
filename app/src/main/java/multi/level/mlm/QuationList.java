package multi.level.mlm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class QuationList extends AppCompatActivity {
    TextView _tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quation_list);
        setTitle("MCQ Test");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e)
        {

        }


         _tv = (TextView) findViewById( R.id.txttimer );
        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                _tv.setText(""+String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                _tv.setText("Time Out!");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuationList.this);
                alertDialogBuilder.setMessage("Sorry, Time Out !");


                alertDialogBuilder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    //Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        }.start();
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

        //startActivity(new Intent(Registration.this,Login.class));
        finish();



    }
}
