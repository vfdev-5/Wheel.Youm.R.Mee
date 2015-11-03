package com.vfdev.wheelyoumrmee;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullscreenVideoActivity extends AppCompatActivity {

    // Ui
    @Bind(R.id.fs_video)
    VideoView videoView;

//    private int mVideoPosition = -1;

    MediaController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);
        ButterKnife.bind(this);

        mController = new MediaController(this, false);
        videoView.setMediaController(mController);

        Intent data = getIntent();
        if (data != null && data.getExtras() != null) {
            try {
                Uri uri = data.getExtras().getParcelable("uri");
                videoView.setVideoURI(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int videoPosition = data.getExtras().getInt("position", -1);
            if (videoPosition > 0) {
                Log.i("FULLSCREEN", "Position : " + videoPosition);
                videoView.seekTo(videoPosition);
            }
        }

//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//
//            }
//        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                setupResult(videoView.getDuration());
                finish();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onPause(){
        Log.i("FULLSCREEN", "onPause");
        videoView.stopPlayback();
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        Log.i("FULLSCREEN", "onBackPressed");
        setupResult(videoView.getCurrentPosition());
        finish();
    }



    private void setupResult(int videoPosition) {

        Intent data = new Intent();
        data.putExtra("position", videoPosition);
        Log.i("FULLSCREEN", "Output position : " + videoPosition);
        setResult(VideoActivity.REQUEST_VIDEO_POSITION, data);
    }



}