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