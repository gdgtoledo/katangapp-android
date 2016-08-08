package es.craftsmanship.toledo.katangapp.interactors;

import java.io.IOException;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.services.KatangaResponseWrapper;

import retrofit2.Response;

/**
 * @author Manuel de la Peña
 */
public class FavoritesInteractor extends BaseKatangaInteractor {

    private final String busStopId;

    public FavoritesInteractor(String busStopId) {
        this.busStopId = busStopId;
    }

    @Override
    protected KatangaResponseWrapper<Response<QueryResult>> getResponse(
            BusStopsService busStopsService)
        throws IOException {

        Response<QueryResult> response = busStopsService.favorites(busStopId).execute();

        return new KatangaResponseWrapper<>(response);
    }

}