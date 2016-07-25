package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.activities.BusStopActivity;
import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
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
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class BusStopsAdapter extends RecyclerView.Adapter<BusStopsAdapter.BusStopsViewHolder> {

    private final List<BusStopResult> busStops;
    private Context context;
    private RecyclerView lines;
    private TextView address;
    private ImageView busStopIcon;
    private TextView distance;

    public BusStopsAdapter(List<BusStopResult> busStops) {
        this.busStops = busStops;
    }

    @Override
    public BusStopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.bus_stop_row, parent, false);

        return new BusStopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusStopsViewHolder holder, int position) {
        holder.bind(busStops.get(position));
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }

    public class BusStopsViewHolder extends RecyclerView.ViewHolder {

        private BusStop currentBusStop;

        public BusStopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.stop_address);
            busStopIcon = (ImageView) itemView.findViewById(R.id.logo);
            distance = (TextView) itemView.findViewById(R.id.stop_distance);
            lines = (RecyclerView) itemView.findViewById(R.id.lines);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BusStopActivity.class);

                    intent.putExtra("busStop", currentBusStop);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

            };

            address.setOnClickListener(onClickListener);
            busStopIcon.setOnClickListener(onClickListener);

            address.setTypeface(tf);
            distance.setTypeface(tf);
        }

        public void bind(BusStopResult stop) {
            List<RouteResult> results = stop.getResults();

            int size = results.size();

            lines.setMinimumHeight(size * 100);
            lines.setAdapter(new BusStopRoutesAdapter(results));

            BusStop busStop = stop.getBusStop();

            currentBusStop = busStop;

            address.setText(busStop.getAddress());

            String sb = "(" + String.format(Locale.getDefault(), "%.2f", stop.getDistance()) + " " +
                context.getString(R.string.meters) + ")";

            distance.setText(sb);
        }
    }

}