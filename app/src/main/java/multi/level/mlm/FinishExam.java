package multi.level.mlm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FinishExam extends Activity {

    TextView txttotalearn,txttscor,txtcorrect,txtwrong,txtearn,txtloss,txtfper;
    ProgressBar probar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_exam);



        String correct=getIntent().getExtras().getString("yes");
        String wrong=getIntent().getExtras().getString("no");
        String skip=getIntent().getExtras().getString("skip");
        String loss=getIntent().getExtras().getString("loss");
        String earn=getIntent().getExtras().getString("earn");

        txtfper=(TextView)findViewById(R.id.txtProgress_f);
        probar=(ProgressBar) findViewById(R.id.progressBar_f);
        try{

            int ts=Integer.parseInt(correct)+Integer.parseInt(wrong)+Integer.parseInt(skip);
            Double percentage = ((double)Integer.parseInt(correct)/ts) * 100;
            txtfper.setText(Math.round(percentage)+"%");

            probar.setProgress(Integer.valueOf(percentage.intValue()));

        }catch(Exception e){}
        txttotalearn=(TextView)findViewById(R.id.txttotalearn);
        txttotalearn.setText(""+earn);
        txttscor=(TextView)findViewById(R.id.txt_total_scr);
        txttscor.setText(""+correct);

        txtcorrect=(TextView)findViewById(R.id.txt_correct);
        txtcorrect.setText(""+correct);


        txtwrong=(TextView)findViewById(R.id.txt_wrong);
        txtwrong.setText(""+wrong);


        txtearn=(TextView)findViewById(R.id.txt_earns);
        txtearn.setText(""+earn);
        txtloss=(TextView)findViewById(R.id.txt_loss);
        txtloss.setText(""+loss);

findViewById(R.id.playgain).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.putExtra("MESSAGE","load");
        setResult(2,intent);
        finish();//finishing activity
    }
});

        findViewById(R.id.btback1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("MESSAGE","load");
                setResult(2,intent);
                finish();//finishing activity
            }
        });



        findViewById(R.id.gohome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   Intent intent= new Intent(FinishExam.this,Home_Menu.class);
                   startActivity(intent);
               }catch (Exception e){}
            }
        });


    }
}
