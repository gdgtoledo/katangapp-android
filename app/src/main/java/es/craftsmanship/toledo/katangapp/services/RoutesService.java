package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.Route;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chermida on 12/07/2016.
 */
public interface RoutesService {
    @GET("api/routes/{routeId}")
    Call<Route> getRouteId(@Path("routeId") String routeId);
}
