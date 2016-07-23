package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.adapters.FavoritesAdapter;
import es.craftsmanship.toledo.katangapp.db.FavoriteDAO;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoritesActivity extends BaseAndroidBusRegistrableActivity {

    private RecyclerView recyclerView;

    @Subscribe
    public void favoritesReceived(QueryResult queryResult) {
        Intent intent = new Intent(FavoritesActivity.this, ShowBusStopsActivity.class);

        intent.putExtra("queryResult", queryResult);
        intent.putExtra("favorites", true);

        List<BusStopResult> results = queryResult.getResults();

        // there should be only one
        BusStopResult busStopResult = results.get(0);

        intent.putExtra("busStop", busStopResult.getBusStop());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorites);

        try(FavoriteDAO favoriteDAO = new FavoriteDAO(this)) {
            favoriteDAO.open();

            List<Favorite> favorites = favoriteDAO.getAllFavorites();

            recyclerView = (RecyclerView) findViewById(R.id.favoritesList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new FavoritesAdapter(favorites));
        }

    }

}