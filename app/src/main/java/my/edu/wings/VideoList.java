package my.edu.wings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

public class VideoList extends AppCompatActivity {

    TextView txtnm;
    MxVideoPlayerWidget videoPlayerWidget;
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

        txtnm=(TextView)findViewById(R.id.txtvideonm);
        String str=getIntent().getExtras().getString("nm");
        txtnm.setText("Video Title : "+str);

        String link=getIntent().getExtras().getString("video");

         videoPlayerWidget = (MxVideoPlayerWidget) findViewById(R.id.mpw_video_player);
         try{
        videoPlayerWidget.startPlay(link, MxVideoPlayer.SCREEN_LAYOUT_NORMAL, str);}catch (Exception e){}
       // videoPlayerWidget.setAutoProcessUI();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MxVideoPlayer.releaseAllVideos();
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
        if (MxVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();

        Intent intent=new Intent();
        intent.putExtra("MESSAGE","load");
        setResult(2,intent);
        finish();



    }
}
