package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;

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
    protected Response<QueryResult> getResponse(BusStopsService busStopsService)
        throws IOException {

        return busStopsService.listBusStops(latitude, longitude, radio).execute();
    }

}