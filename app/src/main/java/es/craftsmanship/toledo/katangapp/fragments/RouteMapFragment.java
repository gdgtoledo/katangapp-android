package es.craftsmanship.toledo.katangapp.fragments;

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
}