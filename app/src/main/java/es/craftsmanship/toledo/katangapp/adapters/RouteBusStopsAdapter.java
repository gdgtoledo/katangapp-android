package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.activities.BusStopActivity;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Cristobal Hermida
 */
public class RouteBusStopsAdapter
    extends RecyclerView.Adapter<RouteBusStopsAdapter.RouteBusStopsViewHolder>  {

    private final List<BusStop> busStops;
    private Context context;

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

        return new RouteBusStopsViewHolder(parent);
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }

    public class RouteBusStopsViewHolder extends RecyclerView.ViewHolder {

        private BusStop currentBusStop;
        private TextView address;
        private ImageView busStopIcon;

        public RouteBusStopsViewHolder(ViewGroup parent) {
            super(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bus_line_row, parent, false));

            address = (TextView) itemView.findViewById(R.id.bus_stop_address);
            busStopIcon = (ImageView) itemView.findViewById(R.id.bus_stop_icon);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);

            View.OnClickListener busStopClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BusStopActivity.class);

                    intent.putExtra("busStop", currentBusStop);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

            };

            address.setOnClickListener(busStopClickListener);
            busStopIcon.setOnClickListener(busStopClickListener);
        }

        public void bind(BusStop busStop) {
            currentBusStop = busStop;

            address.setText(currentBusStop.getAddress());
        }

    }

}