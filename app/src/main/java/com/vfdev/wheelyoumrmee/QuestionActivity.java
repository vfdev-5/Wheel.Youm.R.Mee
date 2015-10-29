package com.vfdev.wheelyoumrmee;

import android.app.AlertDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {

    // Ui
    @Bind(R.id.yes)
    ImageButton yes;
    @Bind(R.id.no)
    ImageButton no;




    boolean safeNoButton = true;
    CountDownTimer timer;

    Animations animations = new Animations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);


    }



    @OnClick(R.id.yes)
    public void yes() {
        yes.startAnimation(animations.getButtonAnimation());

        // Display a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.yes_dialog_title)
                .setView(getLayoutInflater().inflate(R.layout.yes_dialog, null));
        AlertDialog dialog = builder.create();
        dialog.show();

        // Notify me

    }


    @OnClick(R.id.no)
    public void no() {

        no.startAnimation(animations.getButtonAnimation());

        if (safeNoButton) {
            Toast.makeText(this,
                    getString(R.string.confirm_no),
                    Toast.LENGTH_LONG).show();

            no.setBackground(getResources().getDrawable(R.drawable.no_red_button));
            safeNoButton = false;
            timer = new CountDownTimer(5000, 2500) {
                @Override
                public void onTick(long l) {
                    // nothing
                }
                @Override
                public void onFinish() {
                    no.setBackground(getResources().getDrawable(R.drawable.no_button));
                    safeNoButton=true;
                }
            };
            timer.start();
            return;
        }

        // Display a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_dialog_title)
                .setView(getLayoutInflater().inflate(R.layout.no_dialog, null));
        AlertDialog dialog = builder.create();
        dialog.show();

        // finish all activities
        timer = new CountDownTimer(5000, 2500) {
            @Override
            public void onTick(long l) {
                // nothing
            }
            @Override
            public void onFinish() {
                 finishAffinity();
            }
        };

    }



}
