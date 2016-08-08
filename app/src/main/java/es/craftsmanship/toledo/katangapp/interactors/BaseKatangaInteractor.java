package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.services.KatangaResponseWrapper;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseKatangaInteractor implements KatangaInteractor {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            BusStopsService service = retrofit.create(BusStopsService.class);

            KatangaResponseWrapper katangaResponseWrapper = getResponse(service);

            Response response = (Response) katangaResponseWrapper.getResponse();

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

    protected abstract KatangaResponseWrapper getResponse(BusStopsService busStopsService)
        throws IOException;

}