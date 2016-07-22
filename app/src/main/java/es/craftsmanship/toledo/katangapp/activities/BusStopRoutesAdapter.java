package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

/**
 * author Cristóbal Hermida
 * author Manuel de la Peña
 */
public class BusStopRoutesAdapter extends RecyclerView.Adapter<BusStopRoutesAdapter.BusStopRoutesHolder> {

    private List<RouteResult> routes;

    public BusStopRoutesAdapter(List<RouteResult> routes) {
        this.routes = routes;
    }

    @Override
    public BusStopRoutesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusStopRoutesHolder(parent);
    }

    @Override
    public void onBindViewHolder(BusStopRoutesHolder holder, int position) {
        holder.bind(routes.get(position));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    static class BusStopRoutesHolder extends RecyclerView.ViewHolder {

        private final TextView lineText;
        private final ViewGroup parent;
        private final TextView timeText;

        public BusStopRoutesHolder(ViewGroup parent) {
            super(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bus_stop_route_row, parent, false));

            this.parent = parent;

            lineText = (TextView) itemView.findViewById(R.id.line);
            timeText = (TextView) itemView.findViewById(R.id.time);
        }

        public void bind(RouteResult route) {
            lineText.setText(route.getIdl());

            formatTimeTextStyles(timeText, route.getTime());
        }

        private void formatTimeTextStyles(TextView textView, long time) {
            int color = Color.BLACK;
            float textSize = KatangaFont.DEFAULT_FONT_SIZE;

            if (time <= 5) {
                color = Color.parseColor("#FF4B45");
                textSize *= 1.2;
            }
            else if (time < 10) {
                color = Color.parseColor("#FFB300");
                textSize *= 1.1;
            }

            textView.setTextColor(color);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            if (time == 0) {
                CharSequence text = parent.getContext().getText(R.string.proximo);

                textView.setText(String.valueOf(text).toUpperCase());

                return;
            }

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ES"));

            String formattedTime = numberFormat.format(time);

            CharSequence minutesText = parent.getContext().getText(R.string.minutes);

            textView.setText(formattedTime + " " + minutesText);
        }

    }

}
