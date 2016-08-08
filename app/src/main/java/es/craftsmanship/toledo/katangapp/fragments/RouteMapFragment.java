package es.craftsmanship.toledo.katangapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouteMapFragment extends SupportMapFragment
    {
    private static final String ARG_LINE_NUMBER = "line_number";


    public RouteMapFragment() {
        // Required empty public constructor
    }
    public static RouteMapFragment newInstance() {
        RouteMapFragment fragment = new RouteMapFragment();
       /* Bundle args = new Bundle();
        args.putSerializable(ARG_LINE_NUMBER, (Serializable) linea);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
      //  return inflater.inflate(R.layout.fragment_route_map, container, false);
    }
}
