package multi.level.mlm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectChapter extends AppCompatActivity {
    String fors="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chapter);
        String chnm=getIntent().getExtras().getString("nm");
         fors=getIntent().getExtras().getString("for");
        setTitle(chnm);
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e)
        {

        }
        findViewById(R.id.layq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(fors.equalsIgnoreCase("Test"))
                    startActivity(new Intent(SelectChapter.this,QuationList.class));
                    else
                        startActivity(new Intent(SelectChapter.this,VideoList.class));



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

        //startActivity(new Intent(Registration.this,Login.class));
        finish();



    }
}
