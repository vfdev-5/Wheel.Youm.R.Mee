package com.vfdev.wheelyoumrmee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity {

    // Ui
    @Bind(R.id.video)
    VideoView videoView;
    @Bind(R.id.videoControl)
    ImageButton videoControl;

    @Bind(R.id.next)
    ImageButton next;
    Animations animations = new Animations();


    private Drawable mPlayButtonDrawable;
    private Drawable mPauseButtonDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        mPlayButtonDrawable = getResources().getDrawable(android.R.drawable.ic_media_play);
        mPauseButtonDrawable = getResources().getDrawable(android.R.drawable.ic_media_pause);

        try {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
            videoView.setVideoURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onResume(){
        videoControl.setImageDrawable(mPlayButtonDrawable);
        super.onResume();
        playVideo();
    }

    @Override
    protected void onPause(){
        videoView.stopPlayback();
        super.onPause();
    }


    @OnClick(R.id.next)
    public void next() {
        next.startAnimation(animations.getButtonAnimation());
        Intent i = new Intent(this, QuestionActivity.class);
        startActivity(i);
    }


    @OnClick(R.id.videoControl)
    public void control() {

        if (videoView.isPlaying()) {
            pauseVideo();
        } else {
            playVideo();
        }
    }


    private void playVideo() {
        videoControl.setImageDrawable(mPauseButtonDrawable);
        videoView.start();
    }

    private void pauseVideo() {
        videoControl.setImageDrawable(mPlayButtonDrawable);
        videoView.pause();
    }

}
