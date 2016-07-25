package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.db.FavoriteDAO;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import android.content.Intent;

import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

/**
 * @author Manuel de la Peña
 */
public class BusStopActivity extends AppCompatActivity {

    private BusStop busStop;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bus_stop);

        Intent intent = getIntent();

        if (intent.hasExtra("busStop") && (intent.getSerializableExtra("busStop") != null)) {
            busStop = (BusStop) intent.getSerializableExtra("busStop");

            String title = busStop.getId() + " - " + busStop.getAddress();

            this.setTitle(title);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

            try (FavoriteDAO favoriteDAO = new FavoriteDAO(this)) {
                favoriteDAO.open();

                Favorite favorite = favoriteDAO.getFavorite(busStop.getId());

                isFavorite = (favorite != null);
            }

            final FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.fab);

            floatingActionButton.setImageDrawable(getFavoritedDrawable(isFavorite));

            floatingActionButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String message = "Parada AÑADIDA a favoritas con éxito";

                    try (FavoriteDAO favoriteDAO = new FavoriteDAO(BusStopActivity.this)) {
                        favoriteDAO.open();

                        if (!isFavorite) {
                            favoriteDAO.createFavorite(busStop);
                        } else {
                            message = "Parada ELIMINADA de favoritas con éxito";

                            Favorite favorite = new Favorite(busStop);

                            favoriteDAO.deleteFavorite(favorite);
                        }

                        isFavorite = !isFavorite;

                        floatingActionButton.setImageDrawable(getFavoritedDrawable(isFavorite));

                        Snackbar.make(
                            view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }

            });

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Drawable getFavoritedDrawable(boolean favorited) {
        if (favorited) {
            return ContextCompat.getDrawable(BusStopActivity.this, android.R.drawable.ic_delete);
        }

        return ContextCompat.getDrawable(BusStopActivity.this, R.drawable.ic_star_favorite_24dp);
    }

}