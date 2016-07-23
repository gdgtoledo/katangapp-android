package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.adapters.FavoritesAdapter;
import es.craftsmanship.toledo.katangapp.db.FavoriteDAO;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

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