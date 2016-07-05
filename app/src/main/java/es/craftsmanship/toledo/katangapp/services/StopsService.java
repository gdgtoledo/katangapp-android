package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Javier Gamarra
 */
public interface StopsService {

    @GET("paradas")
    Call<QueryResult> listStops(
        @Query("lt") Double latitude, @Query("ln") Double longitude, @Query("r") String radius);
}