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




public class RouteMapActivity extends BaseGeoLocatedActivity implements OnMapReadyCallback {

    private static final String CTE_MAPA = "MAPA";
    private static final String CTE_RUTA = "RUTA";

    private GoogleMap mMap;
    private RouteMapFragment mFirstMapFragment;
    private List<BusStop> busStopResults;
    private TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("ruta") &&
                (intent.getSerializableExtra("ruta") != null)) {
            Route route = (Route) intent.getSerializableExtra("ruta");
            setTitle(route.getName());
            busStopResults = route.getBusStops();
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),route);
        }else {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),null);
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_route_map, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        if (!busStopResults.isEmpty()) {

            for (BusStop stop : busStopResults) {
                LatLng locStop = new LatLng(stop.getLatitude(), stop.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(locStop)
                        .title(stop.getAddress()));
            }
        }
        LatLng locStopIni=null;
        if(getLatitude()!=null && getLongitude()!=null){
            locStopIni  = new LatLng( getLatitude(), getLongitude());
        }else{
            BusStop busStop=   busStopResults.get(0);
            locStopIni  = new LatLng(  busStop.getLatitude(), busStop.getLongitude());
        }

        mMap.addMarker(new MarkerOptions()
                .position(locStopIni)
                .title("Mi situación")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(locStopIni)
                .zoom(12)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
    public void busStopsReceived(QueryResult queryResult) {

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
                    mFirstMapFragment.newInstance().getMapAsync(RouteMapActivity.this);
                    return mFirstMapFragment;

                case 1:
                    return RouteFragment.newInstance(route);

            }
            return null;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return CTE_MAPA;
                case 1:
                    return CTE_RUTA;
            }
            return null;
        }
    }
}
