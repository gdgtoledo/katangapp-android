package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.RouteResult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import java.text.NumberFormat;

import java.util.List;

/**
 * author Cristóbal Hermida
 */
public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.LinesHolder> {

    private List<RouteResult> lines;

    public LinesAdapter(List<RouteResult> lines) {
        this.lines = lines;
    }

    @Override
    public LinesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinesHolder(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(LinesHolder holder, int position) {
        holder.bind(lines.get(position));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    static class LinesHolder extends RecyclerView.ViewHolder {

        private final TextView line;
        private final TextView time;

        public LinesHolder(View itemView) {
            super(itemView);

            line = (TextView) itemView.findViewById(R.id.line);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        public void bind(RouteResult route) {
            line.setText(route.getIdl());

            NumberFormat numberFormat = NumberFormat.getInstance();

            String formattedTime = numberFormat.format(route.getTime());

            time.setText(formattedTime);
        }
    }

}
