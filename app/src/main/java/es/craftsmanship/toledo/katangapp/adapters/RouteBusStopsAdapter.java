package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.models.BusStop;
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
 * @author Cristobal Hermida
 */
public class RouteBusStopsAdapter
    extends RecyclerView.Adapter<RouteBusStopsAdapter.RouteBusStopsViewHolder>  {

    private TextView address;
    private Context context;
    private final List<BusStop> busStops;

    public RouteBusStopsAdapter(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    @Override
    public void onBindViewHolder(RouteBusStopsViewHolder holder, int position) {
        holder.bind(busStops.get(position));
    }

    @Override
    public RouteBusStopsViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType) {

        context = parent.getContext();

        View v = LayoutInflater.from(context).inflate(R.layout.bus_line_row, parent, false);

        return new RouteBusStopsViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }

    public class RouteBusStopsViewHolder extends RecyclerView.ViewHolder {

        public RouteBusStopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.bus_line_stop_name);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);
        }

        public void bind(BusStop busStop) {
            address.setText(busStop.getAddress());
        }

    }

}


