package es.craftsmanship.toledo.katangapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import es.craftsmanship.toledo.katangapp.activities.R;
import es.craftsmanship.toledo.katangapp.models.BusStop;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends SupportMapFragment
    {
    private static final String ARG_LINE_NUMBER = "line_number";


    public MapaFragment() {
        // Required empty public constructor
    }
    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
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
      //  return inflater.inflate(R.layout.fragment_mapa, container, false);
    }
}
