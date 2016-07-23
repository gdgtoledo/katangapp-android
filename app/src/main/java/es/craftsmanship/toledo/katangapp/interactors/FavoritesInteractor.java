package es.craftsmanship.toledo.katangapp.interactors;

import java.io.IOException;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Manuel de la Peña
 */
public class FavoritesInteractor implements Runnable {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";
    private final String busStopId;

    public FavoritesInteractor(String busStopId) {
        this.busStopId = busStopId;
    }

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            BusStopsService service = retrofit.create(BusStopsService.class);

            Response<QueryResult> response = service.favorites(busStopId).execute();

            Object event = new Error(response.message());

            if (response.isSuccessful()) {
                event = response.body();
            }

            AndroidBus.getInstance().post(event);
        }
        catch (IOException e) {
            AndroidBus.getInstance().post(e);
        }
    }

}