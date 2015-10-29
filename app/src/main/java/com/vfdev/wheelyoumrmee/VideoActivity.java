package com.vfdev.wheelyoumrmee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
    @Bind(R.id.next)
    ImageButton next;
    Animations animations = new Animations();

    private ProgressDialog progress;
//    private MediaController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        controller = new MediaController(this);



        // setup UI
//        SharedPreferences pref = getSharedPreferences("WYRM", 0);
//        next.setEnabled(pref.getBoolean("video_has_seen", false));

    }


    @OnClick(R.id.next)
    public void next() {
        next.startAnimation(animations.getButtonAnimation());
        Intent i = new Intent(this, QuestionActivity.class);
        startActivity(i);
    }


    private void videoHasSeen() {
        if (!next.isEnabled()) {
//            writePreferences();
            next.setEnabled(true);
        }
    }

    private void writePreferences() {
        SharedPreferences pref = getSharedPreferences("WYRM", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("video_has_seen", true);
        editor.apply();
    }



}
