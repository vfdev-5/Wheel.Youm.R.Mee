package com.vfdev.wheelyoumrmee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {

    // Ui
    @Bind(R.id.yes)
    ImageButton yes;
    @Bind(R.id.no)
    ImageButton no;
    @Bind(R.id.question)
    View question;
    @Bind(R.id.answer_is_yes)
    View answerIsYes;
    @Bind(R.id.progress)
    ProgressBar progress;


    @Bind(R.id.images)
    ViewPager viewPager;

    ImagePagerAdapter adapter;

    Integer [] images = {
            R.drawable.test_i0,
            R.drawable.test_i1,
            R.drawable.test_i2,
            R.drawable.test_i3,
            R.drawable.test_i4,
    };



    public static final int YES = 0;
    public static final int NO = 1;

    boolean safeNoButton = true;
    CountDownTimer timer;

    Animations animations = new Animations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);


        // setup ui
        SharedPreferences pref = getSharedPreferences("WYRM", 0);
        switch (pref.getInt("answer", -1)) {
            case YES:
                answerIsYes.setVisibility(View.VISIBLE);
                question.setVisibility(View.GONE);
                break;
            case NO:
                answerIsYes.setVisibility(View.GONE);
                question.setVisibility(View.GONE);
                break;
            case -1:
            default:
                answerIsYes.setVisibility(View.GONE);
                question.setVisibility(View.VISIBLE);
        }


        setupPager();

    }


    @OnClick(R.id.yes)
    public void yes() {
        yes.startAnimation(animations.getButtonAnimation());

        writePreference(YES);

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

        writePreference(NO);

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
        timer.start();
    }

    private void writePreference(int value) {
        SharedPreferences pref = getSharedPreferences("WYRM", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("answer", value);
        editor.apply();
    }



    private void setupPager() {
        adapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                progress.setProgress(viewPager.getCurrentItem());
            }
        });
    }


//    private void setupSeekBar() {
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser) {
//
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // nothing
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // nothing
//            }
//        });
//
//    }


    // ------------
    private class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<ImageView> mImageViews;
        private ArrayList<Integer> mDrawables;

        public ImagePagerAdapter(Context context, Integer [] drawables) {
            mContext = context;
            mDrawables = new ArrayList<>(Arrays.asList(drawables));
            mImageViews = new ArrayList<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView view = getImageView(position);
            if (view == null) {
                view = createImageView(position);
                if (position == mImageViews.size()) {
                    mImageViews.add(view);
                }
            }
            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public int getCount() {
            return mDrawables.size();
        }

        @Override
        public boolean isViewFromObject (View view, Object object) {
            return view == ((ImageView) object);
        }


        private ImageView getImageView(int id) {
            if (id < 0 || id >= mImageViews.size()) {
                return null;
            }
            return mImageViews.get(id);
        }

        private ImageView createImageView(int id) {
            ImageView view = new ImageView(mContext);
            if (id >=0 && id < mDrawables.size()) {
                view.setImageResource(mDrawables.get(id));
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            return view;
        }

    }


}
