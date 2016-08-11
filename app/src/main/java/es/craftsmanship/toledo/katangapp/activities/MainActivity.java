package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.interactors.BusStopsInteractor;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.subscribers.BusStopsSubscriber;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import at.markushi.ui.CircleButton;

import android.Manifest;

import android.app.Dialog;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Typeface;

import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

/**
 * @author Cristóbal Hermida
 * @author Javier Gamarra
 */
public class MainActivity extends BaseGeoLocatedActivity implements BusStopsSubscriber {

    private static final int DEFAULT_RADIO = 500;
    private static final String TAG = "KATANGAPP";

    private DrawerLayout drawerLayout;
    private CircleButton searchButton;
    private ProgressBar searchProgressBar;
    private SeekBar seekBar;
    private String radio;
    private TextView radioLabel;

    @Subscribe
    public void busStopsReceived(Error error) {
        toggleVisualComponents(true);

        Log.e(TAG, "Error calling server ", error);

        View content = findViewById(android.R.id.content);

        Snackbar.make(content, "Error finding the nearest stop", Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void busStopsReceived(QueryResult queryResult) {
        Intent intent = new Intent(MainActivity.this, ShowBusStopsActivity.class);

        intent.putExtra("queryResult", queryResult);
        intent.putExtra("radio", radio);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);

        toggleVisualComponents(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nav_menu_layout);

        addToolbar();

        initializeRuntimePermissions();

        initializeVariables();

        initializeSeekTrack();

        initializeClickableComponents();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepareDrawer(navigationView);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
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

    private void initializeClickableComponents() {
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence charSequence = radioLabel.getText();

                radio = charSequence.toString();

                if (radio.isEmpty()) {
                    radio = String.valueOf(DEFAULT_RADIO);
                }

                toggleVisualComponents(false);

                BusStopsInteractor busStopsInteractor = new BusStopsInteractor(
                    radio, getLatitude(), getLongitude());

                new Thread(busStopsInteractor).start();
            }

        });

    }

    private void initializeRuntimePermissions() {
        int requestCode = PackageManager.PERMISSION_GRANTED;

        String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        };

        checkRuntimePermissions(permissions, requestCode);
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

    /**
     * A private method to help us initialize our variables
     */
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        radioLabel = (TextView) findViewById(R.id.radioLabel);
        searchButton = (CircleButton) findViewById(R.id.searchButton);
        searchProgressBar = (ProgressBar) findViewById(R.id.searchProgressBar);

        toggleVisualComponents(true);

        Typeface tf = KatangaFont.getFont(getAssets(), KatangaFont.QUICKSAND_REGULAR);

        TextView txtKatangaLabel = (TextView) findViewById(R.id.title_katanga);

        txtKatangaLabel.setTypeface(tf);
        radioLabel.setTypeface(tf);
    }

    private void prepareDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        selectItem(menuItem);

                        drawerLayout.closeDrawers();

                        return true;
                    }
                }

        );
    }

    private void selectItem(MenuItem itemDrawer) {
        switch (itemDrawer.getItemId()) {
            case R.id.item_info:
                showInfoOverLay();

                break;
            case R.id.item_favs:
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);

                startActivity(intent);

                break;
        }

        setTitle(itemDrawer.getTitle());
    }

    /**
     * Shows the overlay dialog showing the application information.
     */
    private void showInfoOverLay() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);

        dialog.setContentView(R.layout.overlay_view);

        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.overlayLayout);

        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }

        });

        dialog.show();
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