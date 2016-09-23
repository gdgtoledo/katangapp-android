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

package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

import es.craftsmanship.toledo.katangapp.models.Route;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Javier Gamarra
 * @author Cristóbal Hermida
 */
public interface BusStopsService {

    @GET("main")
    Call<QueryResult> listBusStops(
        @Query("lt") Double latitude, @Query("ln") Double longitude, @Query("r") String radius);

    @GET("favorite/{busStopId}")
    Call<QueryResult> favorites(@Path("busStopId") String busStopId);

    @GET("api/routes/{routeId}")
    Call<Route> getRouteId(@Path("routeId") String routeId);

}