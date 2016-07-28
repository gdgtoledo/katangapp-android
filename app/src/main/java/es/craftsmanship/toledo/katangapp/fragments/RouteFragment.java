package es.craftsmanship.toledo.katangapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import java.io.Serializable;
import java.util.List;

import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.models.BusStop;


public class RouteFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_LINE_NUMBER = "line_number";

        public RouteFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RouteFragment newInstance(List<BusStop> linea) {
            RouteFragment fragment = new RouteFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_LINE_NUMBER, (Serializable) linea);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_route, container, false);

         /* List<BusStop>  ruta= (List<BusStop>) getArguments().getSerializable(ARG_LINE_NUMBER);
            for(BusStop stop: ruta){
                stop.getAddress();
                stop.getId();
                stop.getRouteId();
            }
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView textViewRuta = (TextView) rootView.findViewById(R.id.section_ruta);


            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            textViewRuta.setText(getArguments().getString(ARG_LINE_NUMBER));*/
            return rootView;
        }
    }

