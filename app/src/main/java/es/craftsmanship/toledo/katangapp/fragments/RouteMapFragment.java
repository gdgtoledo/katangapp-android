package es.craftsmanship.toledo.katangapp.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * @author Cristóbal Hermida
 */
public class RouteMapFragment extends SupportMapFragment {

    public RouteMapFragment() {
    }

    public static RouteMapFragment newInstance() {
        RouteMapFragment fragment = new RouteMapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}