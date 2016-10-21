package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.adapters.RoutesAdapter;
import es.craftsmanship.toledo.katangapp.models.Route;
import es.craftsmanship.toledo.katangapp.subscribers.RouteSubscriber;
import es.craftsmanship.toledo.katangapp.subscribers.RoutesSubscriber;
import es.craftsmanship.toledo.katangapp.utils.ExtrasConstants;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.otto.Subscribe;

/**
 * @author Manuel de la Peña
 */
public class RoutesActivity
    extends BaseAndroidBusRegistrableActivity
    implements RouteSubscriber, RoutesSubscriber {

    private RecyclerView recyclerView;

    @Override
    @Subscribe
    public void routeReceived(Error error) {
    }

    @Override
    @Subscribe
    public void routeReceived(Route route) {
        if (route != null) {
            Intent intent = new Intent(this, RouteMapActivity.class);

            intent.putExtra(ExtrasConstants.ROUTE, route);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }

    @Override
    @Subscribe
    public void routesReceived(Error error) {
    }

    @Override
    @Subscribe
    public void routesReceived(Route[] routes) {
        recyclerView.setAdapter(new RoutesAdapter(routes));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_routes);

        Intent intent = getIntent();

        if (intent.hasExtra(ExtrasConstants.ROUTES) &&
            (intent.getSerializableExtra(ExtrasConstants.ROUTES) != null)) {

            Route[] routes = (Route[]) intent.getSerializableExtra(
                ExtrasConstants.ROUTES);

            recyclerView = (RecyclerView) findViewById(R.id.routesList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new RoutesAdapter(routes));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}