package es.craftsmanship.toledo.katangapp.fragments;

import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.adapters.LineBusStopAdapter;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Cristóbal Hermida
 */
public class RouteFragment extends Fragment {

    private static final String ARG_LINE_NUMBER = "line_number";

    private RecyclerView.LayoutManager layoutManager;
    private List<BusStop> lineStops;
    private RecyclerView recyclerView;

    public RouteFragment() {
    }

    public static RouteFragment newInstance(Route ruta) {
        RouteFragment fragment = new RouteFragment();

        Bundle args = new Bundle();

        args.putSerializable(ARG_LINE_NUMBER, ruta);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_route, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.line_stops);

        Route  ruta= (Route) getArguments().getSerializable(ARG_LINE_NUMBER);

        if(ruta!=null){
            lineStops= ruta.getBusStops();
        }

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LineBusStopAdapter(lineStops));

        return rootView;
    }

}