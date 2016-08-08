package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.Route;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chermida on 12/07/2016.
 */
public interface RoutesService {
    @GET("api/routes/{routeId}")
    Call<Route> getRouteId(@Path("routeId") String routeId);
}
