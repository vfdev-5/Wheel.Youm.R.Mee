package com.vfdev.wheelyoumrmee;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import butterknife.Bind;

public class VideoActivity extends AppCompatActivity {

    // Ui
    @Bind(R.id.video)
    VideoView videoView;

    private ProgressDialog progress;
//    private MediaController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        controller = new MediaController(this);

    }


}
