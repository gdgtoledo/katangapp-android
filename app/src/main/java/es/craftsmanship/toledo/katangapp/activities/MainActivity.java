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

    private SeekBar seekBar;
    private TextView txtKatangaLabel;
    private ImageView button;
    private TextView txtradiolabel;
    private ProgressBar progressBar;
    private ServiceConnectionTask task = null;


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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radio = (String) txtradiolabel.getText().toString();
                String url = "http://secret-depths-4660.herokuapp.com/paradas?lt=39.862658&ln=-4.025088&r=" + radio;
                // paradas?lt=39.862658&ln=-4.025088&r=500
                task = (ServiceConnectionTask) new ServiceConnectionTask().execute(new String[] {url});

            }
        });


    }

    // AsyncTask <TypeOfVarArgParams , ProgressValue , ResultValue> .
    private class ServiceConnectionTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

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
    }


    // A private method to help us initialize our variables.
    private void initializeVariables() {

        // Font path
        String fontPath = "fonts/Quicksand-Regular.ttf";

        // text view label
        txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtradiolabel = (TextView)findViewById(R.id.txtradiolabel);
        button = (ImageView) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtKatangaLabel.setTypeface(tf);
        txtradiolabel.setTypeface(tf);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (task != null && task.getStatus() != ServiceConnectionTask.Status.FINISHED) {
            task.cancel(true);
            task = null;
        }
    }

}