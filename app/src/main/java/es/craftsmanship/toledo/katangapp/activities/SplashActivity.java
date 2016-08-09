package es.craftsmanship.toledo.katangapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

/**
 * author Cristóbal Hermida
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    private TextView splashTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        splashTitle = (TextView) findViewById(R.id.splashTitle);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        splashTitle.setTypeface(tf);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(i);

                finish();
            }

        }, SPLASH_TIME_OUT);
    }

}