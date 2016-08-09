package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.db.FavoriteDAO;
import es.craftsmanship.toledo.katangapp.db.model.Favorite;
import es.craftsmanship.toledo.katangapp.interactors.FavoritesInteractor;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.content.Context;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private final List<Favorite> favorites;

    private TextView address;
    private TextView busStopId;
    private ImageView busStopLogo;
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

    public void remove(int position) {
        try(FavoriteDAO favoriteDAO = new FavoriteDAO(context)) {
            favoriteDAO.open();

            Favorite favorite = favorites.get(position);

            favoriteDAO.deleteFavorite(favorite);

            favorites.remove(position);

            notifyItemRemoved(position);

            Toast.makeText(
                context, context.getString(R.string.favorite_deleted_ok), Toast.LENGTH_SHORT)
            .show();
        }
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private String busStopIdText;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            busStopId = (TextView) itemView.findViewById(R.id.busStopId);
            busStopLogo = (ImageView) itemView.findViewById(R.id.busStopLogo);
            address = (TextView) itemView.findViewById(R.id.busStopAddress);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            busStopId.setTypeface(tf);
            address.setTypeface(tf);

            View.OnClickListener busStopClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FavoritesInteractor favoritesInteractor = new FavoritesInteractor(
                        busStopIdText);

                    new Thread(favoritesInteractor).start();
                }

            };

            busStopId.setOnClickListener(busStopClickListener);
            address.setOnClickListener(busStopClickListener);
            busStopLogo.setOnClickListener(busStopClickListener);
        }

        public void bind(Favorite favorite) {
            busStopIdText = favorite.getBusStopId();

            busStopId.setText(favorite.getBusStopId());
            address.setText(favorite.getAddress());
        }
    }

}