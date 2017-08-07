/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.route_bus_stops);

        Route route = (Route) getArguments().getSerializable(ARG_LINE_NUMBER);

        if (route != null) {
            busStops = route.getBusStops();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RouteBusStopsAdapter(busStops));

        return rootView;
    }

}