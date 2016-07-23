package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.utils.AndroidBus;

import android.support.v7.app.AppCompatActivity;

/**
 * @author Manuel de la Peña
 */
public class BaseAndroidBusRegistrableActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        AndroidBus.getInstance().unregister(this);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AndroidBus.getInstance().register(this);
    }

}