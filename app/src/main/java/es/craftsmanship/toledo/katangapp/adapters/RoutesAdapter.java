package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.interactors.RouteIdInteractor;
import es.craftsmanship.toledo.katangapp.models.Route;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.content.Context;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private final List<Route> routes;

    private Context context;

    public RoutesAdapter(Route[] routes) {
        this.routes = new ArrayList<>();

        for (Route route : routes) {
            this.routes.add(route);
        }
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new RouteViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        holder.bind(routes.get(position));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        private String routeIdText;
        private TextView routeId;
        private TextView routeName;

        public RouteViewHolder(ViewGroup parent) {
            super(
                LayoutInflater.from(context)
                    .inflate(R.layout.route_row, parent, false));

            routeId = (TextView) itemView.findViewById(R.id.routeId);

            routeName = (TextView) itemView.findViewById(R.id.routeName);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            routeId.setTypeface(tf);
            routeName.setTypeface(tf);

            View.OnClickListener busStopClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    RouteIdInteractor routeIdInteractor = new RouteIdInteractor(routeIdText);

                    new Thread(routeIdInteractor).start();
                }

            };

            routeId.setOnClickListener(busStopClickListener);
            routeName.setOnClickListener(busStopClickListener);

            ImageView routeLogo = (ImageView) itemView.findViewById(R.id.routeLogo);

            routeLogo.setOnClickListener(busStopClickListener);
        }

        public void bind(Route route) {
            routeIdText = route.getId();

            routeId.setText(route.getId());
            routeName.setText(route.getName());
        }

    }

}