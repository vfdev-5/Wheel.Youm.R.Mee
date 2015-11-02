package com.vfdev.wheelyoumrmee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.MediaController;
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

    private Uri mUri;
    final static int REQUEST_VIDEO_POSITION=1;

    MediaController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        mController = new MediaController(this, false);
        mController.setAnchorView(videoView);
        videoView.setMediaController(mController);

        try {
            mUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
            videoView.setVideoURI(mUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        Log.i("VIDEO_ACTIVITY", "On Resume");
        videoView.start();
    }

    @Override
    protected void onPause(){
        videoView.stopPlayback();
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_VIDEO_POSITION) {
//            if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                int videoPosition = data.getIntExtra("position", -1);
                if (videoPosition > 0) {
                    Log.i("VIDEO_ACTIVITY", "Video : seek to position : " + videoPosition);
                    videoView.seekTo(videoPosition);
//                }
            }
        }
    }


    @OnClick(R.id.next)
    public void next() {
        next.startAnimation(animations.getButtonAnimation());
        Intent i = new Intent(this, QuestionActivity.class);
        startActivity(i);
    }


    @OnClick(R.id.to_fullscreen)
    public void toFullscreenMode() {
        videoView.pause();
        int position = videoView.getCurrentPosition();

        Intent i = new Intent(this, FullscreenVideoActivity.class);
        i.putExtra("position", position);
        i.putExtra("uri", mUri);
        startActivityForResult(i, REQUEST_VIDEO_POSITION);

    }

}
