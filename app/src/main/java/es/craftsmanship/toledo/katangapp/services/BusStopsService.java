package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Javier Gamarra
 */
public interface BusStopsService {

    @GET("main")
    Call<QueryResult> listBusStops(
        @Query("lt") Double latitude, @Query("ln") Double longitude, @Query("r") String radius);

    @GET("favorite/{busStopId}")
    Call<QueryResult> favorites(@Path("busStopId") String busStopId);

}