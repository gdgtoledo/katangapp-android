package es.craftsmanship.toledo.katangapp.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

/**
 * @author Javier Gamarra
 */
public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.StopsViewHolder> {

    private final List<BusStopResult> stops;
    private  Context context;
    private RecyclerView lineas;
    private  TextView address =null;
    private  TextView distance =null;
    public StopsAdapter(List<BusStopResult> stops) {
        this.stops = stops;
    }

    @Override
    public StopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop, parent, false);

        return new StopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopsViewHolder holder, int position) {
        BusStopResult stop = new BusStopResult();
        stop = stops.get(position);
        holder.bind(stop);
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public class StopsViewHolder extends RecyclerView.ViewHolder {


       LinesAdapter linesAdapter;


        public StopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.stop_address);
            distance = (TextView) itemView.findViewById(R.id.stop_distance);
            lineas = (RecyclerView) itemView.findViewById(R.id.lineas);
            lineas.setLayoutManager(new LinearLayoutManager(context));

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);
            distance.setTypeface(tf);

        }

        public void bind(BusStopResult stop) {

            int tam=  stop.getResults().size();
            lineas.setMinimumHeight(tam*100);
            lineas.setAdapter(new LinesAdapter(stop.getResults()));
            address.setText(stop.getBusStop().getAddress());
            String dtc= String.format("%.2f", stop.getDistance());
            dtc="("+dtc+" metros)";
            distance.setText(dtc);


        }
    }

}
