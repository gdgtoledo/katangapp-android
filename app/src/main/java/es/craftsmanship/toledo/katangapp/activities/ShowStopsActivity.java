package es.craftsmanship.toledo.katangapp.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Toast;

/**
 * @author Cristóbal Hermida
 */
public class ShowStopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_stops);

        Intent i = getIntent();
        String stops = i.getStringExtra("stopslist");

        Toast toast = Toast.makeText(getApplicationContext(), stops, Toast.LENGTH_SHORT);

        toast.show();
    }

}