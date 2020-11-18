package my.edu.wings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TermsConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        setTitle("Terms & Conditions");
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

       // startActivity(new Intent(Registration.this,MainActivity.class));
        finish();

    }
}
