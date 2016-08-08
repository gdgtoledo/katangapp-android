package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.fragments.RouteMapFragment;
import es.craftsmanship.toledo.katangapp.fragments.RouteFragment;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.Route;

import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * @author Cristóbal Hermida
 */
public class RouteMapActivity extends BaseGeoLocatedActivity implements OnMapReadyCallback {

    private static final String MAP_KEY = "MAPA";
    private static final String ROUTE_KEY = "RUTA";

    private List<BusStop> busStopResults;
    private GoogleMap googleMap;
    private RouteMapFragment mapFragment;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void busStopsReceived(QueryResult queryResult) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_route_map, menu);

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.getUiSettings().setMapToolbarEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(true);

        if (!busStopResults.isEmpty()) {
            for (BusStop stop : busStopResults) {
                LatLng locStop = new LatLng(stop.getLatitude(), stop.getLongitude());

                this.googleMap.addMarker(
                    new MarkerOptions().position(locStop).title(stop.getAddress()));
            }
        }

        LatLng locStopIni = null;

        if (getLatitude() != null && getLongitude() != null) {
            locStopIni  = new LatLng( getLatitude(), getLongitude());
        }
        else {
            BusStop busStop = busStopResults.get(0);

            locStopIni = new LatLng(busStop.getLatitude(), busStop.getLongitude());
        }

        this.googleMap.addMarker(
            new MarkerOptions()
                .position(locStopIni)
                .title("Mi situación")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(locStopIni)
                .zoom(12)
                .build();

        this.googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), ShowBusStopsActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_route_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent.hasExtra("route") && (intent.getSerializableExtra("route") != null)) {
            Route route = (Route) intent.getSerializableExtra("route");

            setTitle(route.getName());

            busStopResults = route.getBusStops();
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),route);
        }
        else {
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),null);
        }

        viewPager = (ViewPager) findViewById(R.id.container);

        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public Route route;

        public SectionsPagerAdapter(FragmentManager fm, Route route) {
            super(fm);

            this.route=route;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mapFragment = RouteMapFragment.newInstance();
                    mapFragment.getMapAsync(RouteMapActivity.this);
                    return mapFragment;
                case 1:
                    return RouteFragment.newInstance(route);
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return MAP_KEY;
                case 1:
                    return ROUTE_KEY;
            }

            return null;
        }

    }

}