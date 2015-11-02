package com.vfdev.wheelyoumrmee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity
{

    // UI
    @Bind(R.id.submit)
    ImageButton submit;
    @Bind(R.id.answers)
    Spinner answers;
    @Bind(R.id.day)
    EditText day;
    @Bind(R.id.month)
    EditText month;

    Animations animations = new Animations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharedPreferences pref = getSharedPreferences("WYRM", 0);
        if (!pref.getBoolean("is_new_user", true)) {

            if (pref.getInt("answer", -1) == QuestionActivity.NO) {
                Toast.makeText(this,
                        getString(R.string.login_no_answer),
                        Toast.LENGTH_LONG
                        ).show();
                finish();
            } else {
                goToNextActivity();
            }
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.login_q2_vars,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        answers.setAdapter(adapter);

    }


    @OnClick(R.id.submit)
    public void sumbit(){

        submit.startAnimation(animations.getButtonAnimation());
        int d, m;
        try {
            d = Integer.parseInt(day.getText().toString());
            m = Integer.parseInt(month.getText().toString());
        } catch (NumberFormatException e) {
            d = 0;
            m = 0;
        }
        int a = answers.getSelectedItemPosition()+1;

        if (d == m && m == a && a*d*m == 64) {
            // OK
            writePreferences();
            goToNextActivity();
            return;
        }

        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();

    }

    private void writePreferences() {
        SharedPreferences pref = getSharedPreferences("WYRM", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("is_new_user", false);
        editor.apply();
    }

    private void goToNextActivity() {
        Intent i = new Intent(this, VideoActivity.class);
        startActivity(i);
        finish();
    }

}
