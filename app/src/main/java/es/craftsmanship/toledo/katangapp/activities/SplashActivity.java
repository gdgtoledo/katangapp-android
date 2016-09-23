/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        TextView splashTitle = (TextView) findViewById(R.id.splashTitle);

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