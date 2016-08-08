package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.Route;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Cristóbal Hermida
 */
public interface RoutesService {
    @GET("api/routes/{routeId}")
    Call<Route> getRouteId(@Path("routeId") String routeId);
}
