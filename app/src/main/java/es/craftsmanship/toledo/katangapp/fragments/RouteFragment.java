package es.craftsmanship.toledo.katangapp.fragments;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.adapters.RouteBusStopsAdapter;
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

    private List<BusStop> busStops;
    private RecyclerView.LayoutManager layoutManager;
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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.route_bus_stops);

        Route route = (Route) getArguments().getSerializable(ARG_LINE_NUMBER);

        if (route != null) {
            busStops = route.getBusStops();
        }

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RouteBusStopsAdapter(busStops));

        return rootView;
    }

}