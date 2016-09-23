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

package es.craftsmanship.toledo.katangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @author Manuel de la Peña
 */
public class DismissFavoritesTouchHelper extends ItemTouchHelper.SimpleCallback {

    private FavoritesAdapter favoritesAdapter;

    public DismissFavoritesTouchHelper(FavoritesAdapter favoritesAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        this.favoritesAdapter = favoritesAdapter;
    }

    @Override
    public boolean onMove(
        RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
        RecyclerView.ViewHolder target) {

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        favoritesAdapter.remove(viewHolder.getAdapterPosition());
    }

}