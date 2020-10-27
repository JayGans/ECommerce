package multi.level.mlm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class VideoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        setTitle("Video");
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e)
        {

        }
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
