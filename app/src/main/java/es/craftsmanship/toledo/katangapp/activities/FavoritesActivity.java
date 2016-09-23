/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.adapters.FavoritesAdapter;
import es.craftsmanship.toledo.katangapp.adapters.DismissFavoritesTouchHelper;
import es.craftsmanship.toledo.katangapp.db.FavoriteDAO;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.utils.ExtrasConstants;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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

        intent.putExtra(ExtrasConstants.QUERY_RESULT, queryResult);
        intent.putExtra(ExtrasConstants.ACTIVITY_FAVORITES, true);

        List<BusStopResult> results = queryResult.getResults();

        // there should be only one
        BusStopResult busStopResult = results.get(0);

        intent.putExtra(ExtrasConstants.BUS_STOP, busStopResult.getBusStop());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        try(FavoriteDAO favoriteDAO = new FavoriteDAO(this)) {
            favoriteDAO.open();

            List<Favorite> favorites = favoriteDAO.getAllFavorites();

            recyclerView = (RecyclerView) findViewById(R.id.favoritesList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FavoritesAdapter favoritesAdapter = new FavoritesAdapter(favorites);

            setUpDismissFavoritesTouchGesture(favoritesAdapter);

            recyclerView.setAdapter(favoritesAdapter);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpDismissFavoritesTouchGesture(FavoritesAdapter favoritesAdapter) {
        ItemTouchHelper.Callback callback = new DismissFavoritesTouchHelper(favoritesAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);

        helper.attachToRecyclerView(recyclerView);
    }

}