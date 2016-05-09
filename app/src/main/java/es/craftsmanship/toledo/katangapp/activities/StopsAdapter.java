package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.content.Context;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.StopsViewHolder> {

    private final List<BusStopResult> stops;
    private Context context;
    private RecyclerView lines;
    private TextView address;
    private TextView distance;

    public StopsAdapter(List<BusStopResult> stops) {
        this.stops = stops;
    }

    @Override
    public StopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context)
            .inflate(R.layout.stop, parent, false);

        return new StopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopsViewHolder holder, int position) {
        holder.bind(stops.get(position));
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public class StopsViewHolder extends RecyclerView.ViewHolder {

        public StopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.stop_address);
            distance = (TextView) itemView.findViewById(R.id.stop_distance);
            lines = (RecyclerView) itemView.findViewById(R.id.lines);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);
            distance.setTypeface(tf);
        }

        public void bind(BusStopResult stop) {
            List<RouteResult> results = stop.getResults();

            int size = results.size();

            lines.setMinimumHeight(size * 100);
            lines.setAdapter(new LinesAdapter(results));

            BusStop busStop = stop.getBusStop();

            address.setText(busStop.getAddress());

            String sb = "(" + String.format(Locale.getDefault(), "%.2f", stop.getDistance()) + " " +
                context.getString(R.string.meters) + ")";

            distance.setText(sb);
        }
    }

}