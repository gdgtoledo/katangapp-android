package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.adapters.BusStopsAdapter;
import es.craftsmanship.toledo.katangapp.interactors.InvalidInteractorException;
import es.craftsmanship.toledo.katangapp.interactors.KatangaInteractor;
import es.craftsmanship.toledo.katangapp.interactors.KatangaInteractorFactoryUtil;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import android.content.Intent;

import android.graphics.Color;

import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * @author Cristóbal Hermida
 * @author Manuel de la Peña
 */
public class ShowBusStopsActivity extends BaseGeoLocatedActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Subscribe
    public void busStopsReceived(QueryResult queryResult) {
        List<BusStopResult> results = queryResult.getResults();

        recyclerView.setAdapter(new BusStopsAdapter(results));

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_bus_stops);

        Intent intent = getIntent();

        if (intent.hasExtra("queryResult") &&
            (intent.getSerializableExtra("queryResult") != null)) {

            QueryResult queryResult = (QueryResult) intent.getSerializableExtra("queryResult");

            List<BusStopResult> results = queryResult.getResults();

            if (results.isEmpty()) {
                processEmptyResults();

                return;
            }

            recyclerView = (RecyclerView) findViewById(R.id.stops);

            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            initializeSwipeRefreshLayout(intent.getExtras());

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new BusStopsAdapter(results));
        }
        else {
            processEmptyResults();
        }
    }

    private void initializeSwipeRefreshLayout(final Bundle extras) {
        swipeRefreshLayout.setColorSchemeColors(
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                extras.putDouble("latitude", getLatitude());
                extras.putDouble("longitude", getLongitude());

                try {
                    KatangaInteractor interactor =
                        KatangaInteractorFactoryUtil.getInstance().getInteractor(extras);

                    new Thread(interactor).start();
                }
                catch (InvalidInteractorException e) {
                    processErrors(getString(R.string.bustop_results_invalid_args) + e.getMessage());
                }
            }

        });
    }

    private void processEmptyResults() {
        processErrors(getString(R.string.bustop_results_empty));
    }

    private void processErrors(String message) {
        Toast.makeText(ShowBusStopsActivity.this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

}