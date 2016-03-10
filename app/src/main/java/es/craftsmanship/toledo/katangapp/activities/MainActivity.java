package es.craftsmanship.toledo.katangapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.StopsService;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * @author Cristóbal Hermida
 */
public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";
    private static final int DEFAULT_RADIO = 500;
    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;
    private static final String TAG = "KATANGAPP";



    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    private ImageView button;
    private Double longitude;
    private Double latitude;

    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView txtKatangaLabel;
    private TextView txtRadiolabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (checkGooglePlayServices()) {
            buildGoogleApiClient();
        }
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

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence charSequence = txtRadiolabel.getText();

                String radio = charSequence.toString();

                if (radio.isEmpty()) {
                    radio = String.valueOf(DEFAULT_RADIO);
                }

                button.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BACKEND_ENDPOINT)
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();

                StopsService service = retrofit.create(StopsService.class);
               // 39.862658, -4.025088,

               // service.listStops(39.862658, -4.025088, radio).enqueue(new Callback<QueryResult>() {
                service.listStops(latitude, longitude, radio).enqueue(new Callback<QueryResult>() {

                    @Override
                    public void onResponse(
                            Call<QueryResult> call, retrofit2.Response<QueryResult> response) {

                        Intent intent = new Intent(MainActivity.this, ShowStopsActivity.class);

                        intent.putExtra("queryResult", response.body());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        getApplicationContext().startActivity(intent);

                        button.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<QueryResult> call, Throwable t) {
                        button.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);

                        Log.e(TAG, "Error calling server ", t);
                    }
                });

            }

        });
    }

    private boolean checkGooglePlayServices() {

        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
			/*
			* google play services is missing or update is required
			*  return code could be
			* SUCCESS,
			* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
			* SERVICE_DISABLED, SERVICE_INVALID.
			*/
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {

            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Google Play Services must be installed.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    /**
     * A private method to help us initialize our variables
     */
    private void initializeVariables() {
        txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtRadiolabel = (TextView) findViewById(R.id.txtRadiolabel);
        button = (ImageView) findViewById(R.id.button);
        button.setEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        txtKatangaLabel.setTypeface(tf);
        txtRadiolabel.setTypeface(tf);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            longitude = mLastLocation.getLongitude();
            latitude =  mLastLocation.getLatitude();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}