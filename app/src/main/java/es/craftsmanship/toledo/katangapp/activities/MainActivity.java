package es.craftsmanship.toledo.katangapp.activities;

import android.app.Activity;

import android.content.Intent;

import android.graphics.Typeface;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Cristóbal Hermida
 */
public class MainActivity extends Activity {

    private static final String BACKEND_ENDPOINT = "http://secret-depths-4660.herokuapp.com";

    private ImageView button;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private ServiceConnectionTask task = null;
    private TextView txtKatangaLabel;
    private TextView txtRadiolabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeVariables();

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(
                //     getApplicationContext(), "Radio de búsqueda", Toast.LENGTH_SHORT).show();
                //se debe poner el valor más vistoso
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtRadiolabel.setText(String.valueOf(progress));
            }

        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence charSequence = txtRadiolabel.getText();

                String radio = charSequence.toString();

                String url = BACKEND_ENDPOINT + "/paradas?lt=39.862658&ln=-4.025088&r=" + radio;

                ServiceConnectionTask serviceConnectionTask = new ServiceConnectionTask();

                task = (ServiceConnectionTask)serviceConnectionTask.execute(new String[]{url});
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (task != null && task.getStatus() != ServiceConnectionTask.Status.FINISHED) {
            task.cancel(true);

            task = null;
        }
    }

    // AsyncTask <TypeOfVarArgParams , ProgressValue , ResultValue> .
    private class ServiceConnectionTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            String s = null;
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(MainActivity.this, ShowStopsActivity.class);

            intent.putExtra("stopslist",result);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplicationContext().startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }

    // A private method to help us initialize our variables.
    private void initializeVariables() {
        String fontPath = "fonts/Quicksand-Regular.ttf";

        txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtRadiolabel = (TextView)findViewById(R.id.txtRadiolabel);
        button = (ImageView) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        txtKatangaLabel.setTypeface(tf);
        txtRadiolabel.setTypeface(tf);
    }

}