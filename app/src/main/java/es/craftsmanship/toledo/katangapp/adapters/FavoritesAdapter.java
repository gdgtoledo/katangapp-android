package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.content.Context;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private final List<Favorite> favorites;

    private TextView address;
    private TextView busStopId;
    private Context context;

    public FavoritesAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.favorite_row, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.bind(favorites.get(position));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            busStopId = (TextView) itemView.findViewById(R.id.busStopId);
            address = (TextView) itemView.findViewById(R.id.busStopAddress);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            busStopId.setTypeface(tf);
            address.setTypeface(tf);
        }

        public void bind(Favorite favorite) {
            busStopId.setText(favorite.getBusStopId());
            address.setText(favorite.getAddress());
        }
    }

}