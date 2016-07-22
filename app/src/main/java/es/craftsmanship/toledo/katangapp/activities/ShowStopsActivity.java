package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.interactors.BusStopsInteractor;
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
public class ShowStopsActivity extends BaseGeoLocatedActivity {

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

        setContentView(R.layout.activity_show_stops);

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

            String radio = (String) intent.getSerializableExtra("radio");

            initializeSwipeRefreshLayout(radio);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new BusStopsAdapter(results));
        }
        else {
            processEmptyResults();
        }
    }

    private void initializeSwipeRefreshLayout(final String radio) {
        swipeRefreshLayout.setColorSchemeColors(
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                BusStopsInteractor busStopsInteractor = new BusStopsInteractor(
                    radio, getLatitude(), getLongitude());

                new Thread(busStopsInteractor).start();
            }

        });
    }

    private void processEmptyResults() {
        Toast.makeText(
            ShowStopsActivity.this, getString(R.string.bustop_results_empty), Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

}