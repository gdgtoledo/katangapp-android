package es.craftsmanship.toledo.katangapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ShowStopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stops);
        Intent i = getIntent();
        String stops = i.getStringExtra("stopslist");
        Toast.makeText(getApplicationContext(), "Se ha recibido correctamente", Toast.LENGTH_SHORT).show();

    }
}
