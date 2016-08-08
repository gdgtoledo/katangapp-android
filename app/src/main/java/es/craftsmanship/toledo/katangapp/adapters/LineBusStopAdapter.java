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
public class LineBusStopAdapter
    extends RecyclerView.Adapter<LineBusStopAdapter.LineStopsViewHolder>  {

    private TextView address;
    private Context context;
    private final List<BusStop> lineStops;

    public LineBusStopAdapter(List<BusStop> lineStops) {
        this.lineStops = lineStops;
    }

    @Override
    public void onBindViewHolder(LineStopsViewHolder holder, int position) {
        holder.bind(lineStops.get(position));
    }

    @Override
    public LineBusStopAdapter.LineStopsViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType) {

        context = parent.getContext();

        View v = LayoutInflater.from(context).inflate(R.layout.bus_line_row, parent, false);

        return new LineStopsViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return lineStops.size();
    }

    public class LineStopsViewHolder extends RecyclerView.ViewHolder {

        public LineStopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.bus_line_stop_name);

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);
        }

        public void bind(BusStop busLineStop) {
            address.setText(busLineStop.getAddress());
        }

    }

}


