package multi.level.mlm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class PracticeTest extends AppCompatActivity {
    TextView _tv;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_test);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        _tv = (TextView) findViewById( R.id.txtProgress );
        new CountDownTimer(100000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                _tv.setText(""+millisUntilFinished / 1000);
                int p= (int) (millisUntilFinished / 1000);
                progressBar.setProgress(p);
            }

            public void onFinish() {
                _tv.setText("0");

                progressBar.setProgress(0);

            }
        }.start();
    }
}
