package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.services.KatangaResponseWrapper;

import java.io.IOException;

import retrofit2.Response;

/**
 * @author Javier Gamarra
 * @author Manuel de la Peña
 */
public class BusStopsInteractor extends BaseKatangaInteractor {

    private final String radio;
    private final Double latitude;
    private final Double longitude;

    public BusStopsInteractor(String radio, Double latitude, Double longitude) {
        this.radio = radio;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected KatangaResponseWrapper<Response<QueryResult>> getResponse(
            BusStopsService busStopsService)
        throws IOException {

        Response<QueryResult> response = busStopsService.listBusStops(latitude, longitude, radio)
            .execute();

        return new KatangaResponseWrapper<>(response);
    }

}