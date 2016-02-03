package es.craftsmanship.toledo.katangapp.activities;

import android.app.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Cristóbal Hermida
 */
public class MainActivity extends Activity {

    private SeekBar seekBar;
    private TextView txtKatangaLabel;
    private ImageView button;
    private TextView txtradiolabel;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initializeVariables();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                progress = progresValue;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                // Toast.makeText(getApplicationContext(), "Radio de búsqueda", Toast.LENGTH_SHORT).show();
                //se debe poner el valor más vistoso

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                txtradiolabel.setText(String.valueOf(progress));

            }
        });



    }

    // A private method to help us initialize our variables.
    private void initializeVariables() {

        // text view label
        txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtradiolabel = (TextView)findViewById(R.id.txtradiolabel);
        button = (ImageView) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);


    }

}