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

package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.Route;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.services.KatangaResponseWrapper;

import java.io.IOException;

import retrofit2.Response;

/**
 * @author Cristóbal Hermida
 * @author Manuel de la Peña
 */
public class RouteIdInteractor extends BaseKatangaInteractor {

    private final String route;

    public RouteIdInteractor(String route) {
        this.route = route;
    }

    @Override
    protected KatangaResponseWrapper<Response<Route>> getResponse(BusStopsService busStopsService)
        throws IOException {

        Response<Route> response = busStopsService.getRouteId(route).execute();

        return new KatangaResponseWrapper<>(response);
    }

}