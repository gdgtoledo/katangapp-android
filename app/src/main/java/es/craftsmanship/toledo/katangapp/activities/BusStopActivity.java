package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.BusStop;

import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

/**
 * @author Manuel de la Peña
 */
public class BusStopActivity extends AppCompatActivity {

    private BusStop busStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bus_stop);

        Intent intent = getIntent();

        if (intent.hasExtra("busStop") && (intent.getSerializableExtra("busStop") != null)) {
            busStop = (BusStop) intent.getSerializableExtra("busStop");

            String title = busStop.getId() + " (" + busStop.getAddress() + ")";

            this.setTitle(title);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(
                R.id.fab);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // add to favorites database

                    Snackbar.make(
                        view, "Parada añadida a favoritas con éxito", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                }

            });

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}