package es.craftsmanship.toledo.katangapp.interactors;

import java.io.IOException;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.StopsService;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Javier Gamarra
 */
public class StopsInteractor implements Runnable {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";
    private final String radio;

    public StopsInteractor(String radio) {
        this.radio = radio;
    }

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BACKEND_ENDPOINT)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            StopsService service = retrofit.create(StopsService.class);

            Response<QueryResult> response = service.listStops(39.862658, -4.025088, radio).execute();

            AndroidBus.getInstance().post(response.body());

        } catch (IOException e) {
            AndroidBus.getInstance().post(e);
        }
    }
}
