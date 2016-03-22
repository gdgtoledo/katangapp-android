package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.interactors.StopsInteractor;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;

import android.graphics.Typeface;

import android.location.Location;

import android.os.Bundle;

import android.support.design.widget.Snackbar;

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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.squareup.otto.Subscribe;

/**
 * @author Cristóbal Hermida
 * @author Javier Gamarra
 */
public class MainActivity extends Activity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    private static final int DEFAULT_RADIO = 500;

    private static final LocationRequest GPS_REQUEST = LocationRequest.create()
        .setInterval(3000)
        .setFastestInterval(16)
        .setNumUpdates(3)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private static final String TAG = "KATANGAPP";

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private ImageView button;
    private GoogleApiClient googleApiClient;
    private Double longitude;
    private Double latitude;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView txtRadioLabel;

    @Override
    public void onClick(View v) {
        CharSequence charSequence = txtRadioLabel.getText();

        String radio = charSequence.toString();

        if (radio.isEmpty()) {
            radio = String.valueOf(DEFAULT_RADIO);
        }

        button.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        StopsInteractor stopsInteractor = new StopsInteractor(radio, latitude, longitude);
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
    public void stopsReceived(Error error) {
        button.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);

        Log.e(TAG, "Error calling server ", error);

        View content = findViewById(android.R.id.content);
        Snackbar.make(content, "Error finding the nearest stop", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null) {
            longitude = lastLocation.getLongitude();
            latitude = lastLocation.getLatitude();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
            googleApiClient, GPS_REQUEST, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_RECOVER_PLAY_SERVICES) {
            return;
        }

        if (resultCode == RESULT_OK) {
            if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                googleApiClient.connect();
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(
                this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeGooglePlayServices();

        initializeVariables();

        initializeSeekTrack();

        initializeButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        AndroidBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        AndroidBus.getInstance().unregister(this);

        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onPause();
    }

    private void initializeSeekTrack() {
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtRadioLabel.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });
    }

    private void initializeButton() {
        button.setOnClickListener(this);
    }

    private void initializeGooglePlayServices() {
        int checkGooglePlayServices = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
            /*
            * google play services is missing or update is required
            *  return code could be
            * SUCCESS,
            * SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
            * SERVICE_DISABLED, SERVICE_INVALID.
            */
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                checkGooglePlayServices, this, REQUEST_CODE_RECOVER_PLAY_SERVICES);

            errorDialog.show();

            return;
        }

        googleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
    }

    /**
     * A private method to help us initialize our variables
     */
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtRadioLabel = (TextView) findViewById(R.id.txtRadioLabel);
        button = (ImageView) findViewById(R.id.button);
        button.setEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        TextView txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);

        txtKatangaLabel.setTypeface(tf);
        txtRadioLabel.setTypeface(tf);
    }

}