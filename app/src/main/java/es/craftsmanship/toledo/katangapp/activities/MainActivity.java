package es.craftsmanship.toledo.katangapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import es.craftsmanship.toledo.katangapp.interactors.StopsInteractor;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;


/**
 * @author Cristóbal Hermida
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final int DEFAULT_RADIO = 500;
    private static final String TAG = "KATANGAPP";

    private ImageView button;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView txtRadiolabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeVariables();

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtRadiolabel.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(
                //     getApplicationContext(), "Radio de búsqueda", Toast.LENGTH_SHORT).show();
                //se debe poner el valor más vistoso
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CharSequence charSequence = txtRadiolabel.getText();

        String radio = charSequence.toString();

        if (radio.isEmpty()) {
            radio = String.valueOf(DEFAULT_RADIO);
        }

        button.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        StopsInteractor stopsInteractor = new StopsInteractor(radio);
        new Thread(stopsInteractor).start();
    }

    @Subscribe
    public void stopsReceived(QueryResult queryResult) {
        Intent intent = new Intent(MainActivity.this, ShowStopsActivity.class);

        intent.putExtra("queryResult", queryResult);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);

        button.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Subscribe
    public void stopsReceived(Throwable throwable) {
        button.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);

        Log.e(TAG, "Error calling server ", throwable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        AndroidBus.getInstance().unregister(this);
        super.onPause();
    }

    /**
     * A private method to help us initialize our variables
     */
    private void initializeVariables() {
        TextView txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtRadiolabel = (TextView) findViewById(R.id.txtRadiolabel);
        button = (ImageView) findViewById(R.id.button);
        button.setEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        txtKatangaLabel.setTypeface(tf);
        txtRadiolabel.setTypeface(tf);
    }


}