package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.interactors.StopsInteractor;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import at.markushi.ui.CircleButton;

import android.Manifest;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Typeface;

import android.location.Location;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.util.Log;

import android.view.View;

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
        LocationListener {

    private static final int DEFAULT_RADIO = 500;

    private static final LocationRequest GPS_REQUEST = LocationRequest.create()
        .setInterval(3000)
        .setFastestInterval(16)
        .setNumUpdates(3)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private static final String TAG = "KATANGAPP";

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private CircleButton searchButton;
    private CircleButton helpButton;
    private GoogleApiClient googleApiClient;
    private Double longitude;
    private Double latitude;
    private ProgressBar searchProgressBar;
    private SeekBar seekBar;
    private TextView radioLabel;

    @Subscribe
    public void stopsReceived(QueryResult queryResult) {
        Intent intent = new Intent(MainActivity.this, ShowStopsActivity.class);

        intent.putExtra("queryResult", queryResult);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);

        toggleVisualComponents(true);
    }

    @Subscribe
    public void stopsReceived(Error error) {
        toggleVisualComponents(true);

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

    private void checkRuntimePermissions(String[] permissions, int requestCode) {
        boolean explanation = false;

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    explanation = true;
                }
                else {
                    // No explanation needed, we can request the permission.

                    // requestCode is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }

        if (!explanation) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
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

        initializeRuntimePermissions();

        initializeGooglePlayServices();

        initializeVariables();

        initializeSeekTrack();

        initializeClickableComponents();
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
        radioLabel.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radioLabel.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                searchButton.setPressed(true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                searchButton.setPressed(false);
            }

        });
    }

    private void initializeClickableComponents() {
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence charSequence = radioLabel.getText();

                String radio = charSequence.toString();

                if (radio.isEmpty()) {
                    radio = String.valueOf(DEFAULT_RADIO);
                }

                toggleVisualComponents(false);

                StopsInteractor stopsInteractor = new StopsInteractor(radio, latitude, longitude);

                new Thread(stopsInteractor).start();
            }

        });

        helpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);

                startActivity(intent);
            }

        });
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

    private void initializeRuntimePermissions() {
        int requestCode = PackageManager.PERMISSION_GRANTED;

        String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        };

        checkRuntimePermissions(permissions, requestCode);
    }

    /**
     * A private method to help us initialize our variables
     */
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        radioLabel = (TextView) findViewById(R.id.radioLabel);
        searchButton = (CircleButton) findViewById(R.id.searchButton);
        helpButton = (CircleButton) findViewById(R.id.helpButton);
        searchProgressBar = (ProgressBar) findViewById(R.id.searchProgressBar);

        toggleVisualComponents(true);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        TextView txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);

        txtKatangaLabel.setTypeface(tf);
        radioLabel.setTypeface(tf);
    }

    private void toggleVisualComponents(boolean buttonEnabled) {
        searchButton.setEnabled(buttonEnabled);

        int visibility = View.VISIBLE;

        radioLabel.setVisibility(visibility);

        if (buttonEnabled) {
            visibility = View.INVISIBLE;
        }

        searchProgressBar.setVisibility(visibility);
    }

}