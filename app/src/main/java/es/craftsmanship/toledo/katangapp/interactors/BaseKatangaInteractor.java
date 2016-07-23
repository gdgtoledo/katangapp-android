package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseKatangaInteractor implements Runnable {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            BusStopsService service = retrofit.create(BusStopsService.class);

            Response<QueryResult> response = getResponse(service);

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

    protected abstract Response<QueryResult> getResponse(BusStopsService busStopsService)
        throws IOException;

}