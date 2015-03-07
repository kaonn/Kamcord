package yue.kamcordproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer;

public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        String video_url = intent.getStringExtra("url");

        VideoView v = (VideoView) findViewById(R.id.video_view);
        v.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub

               finish();
            }
        });
        MediaController mc = new MediaController(this);
        mc.setAnchorView(v);
        v.setMediaController(mc);
        mc.setAnchorView(v);
        mc.setMediaPlayer(v);
        v.setMediaController(mc);
        v.setVideoURI(Uri.parse(video_url));
        v.start();


    }

}