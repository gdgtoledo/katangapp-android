package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.BusStopResult;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.StopsViewHolder> {

    private final List<BusStopResult> stops;

    public StopsAdapter(List<BusStopResult> stops) {
        this.stops = stops;
    }

    @Override
    public StopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop, parent, false);

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

        private final TextView address;
        private final TextView distance;

        public StopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.stop_address);
            distance = (TextView) itemView.findViewById(R.id.stop_distance);
        }

        public void bind(BusStopResult stop) {
            address.setText(stop.getBusStop().getAddress());
            distance.setText(String.valueOf(stop.getDistance()));
        }
    }

}
