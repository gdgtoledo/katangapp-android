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

import java.io.Serializable;
import java.util.List;




public class RouteFragment extends Fragment {

        private static final String ARG_LINE_NUMBER = "line_number";
        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        private List<BusStop> lineStops;

        public RouteFragment() {
        }


        public static RouteFragment newInstance(Route ruta) {
            RouteFragment fragment = new RouteFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_LINE_NUMBER, (Serializable) ruta);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_route, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.line_stops);
            Route  ruta= (Route) getArguments().getSerializable(ARG_LINE_NUMBER);
            if(ruta!=null){
              lineStops= ruta.getBusStops();
            }

            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(new LineBusStopAdapter(lineStops));

            return rootView;

        }
    }

