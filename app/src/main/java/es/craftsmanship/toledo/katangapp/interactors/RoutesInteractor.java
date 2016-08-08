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
public class RoutesInteractor extends BaseKatangaInteractor {

    private final String route;

    public RoutesInteractor(String route) {
        this.route = route;
    }

    @Override
    protected KatangaResponseWrapper<Response<Route>> getResponse(BusStopsService busStopsService)
        throws IOException {

        Response<Route> response = busStopsService.getRouteId(route).execute();

        return new KatangaResponseWrapper<>(response);
    }

}