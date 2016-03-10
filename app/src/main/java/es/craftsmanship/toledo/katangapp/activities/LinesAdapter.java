package es.craftsmanship.toledo.katangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.craftsmanship.toledo.katangapp.models.RouteResult;

/**
 * Created by cristobalhp on 25/02/2016.
 */
public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.LinesHolder> {

    private int sizecard;
    private static List<RouteResult> list;

    public LinesAdapter(List<RouteResult> list){
        this.list = list;
       
    }

    @Override
    public LinesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new LinesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false));

    }

    @Override
    public void onBindViewHolder(LinesHolder holder, int position) {
        holder.bind(list.get(position),position);
    }

    @Override
    public int getItemCount() {

       return list.size();
    }

    static class LinesHolder extends RecyclerView.ViewHolder{
        private final TextView linea;
        private final TextView tiempo;

        public LinesHolder(View itemView) {
            super(itemView);
            linea = (TextView) itemView.findViewById(R.id.linea);
            tiempo = (TextView) itemView.findViewById(R.id.tiempo);



        }

        public void bind(RouteResult route,int positcion) {

            linea.setText(route.getIdl());
            tiempo.setText(String.valueOf(route.getTime()));


        }
    }

}
