package es.craftsmanship.toledo.katangapp.interactors;

import java.io.IOException;

import es.craftsmanship.toledo.katangapp.models.Route;
import es.craftsmanship.toledo.katangapp.services.RoutesService;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by chermida on 12/07/2016.
 */
public class RoutesInteractor implements Runnable{
    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";
    private final String route;

    public RoutesInteractor(String route) {
        this.route = route;
    }

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BACKEND_ENDPOINT)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            RoutesService service = retrofit.create(RoutesService.class);

            Response<Route> response = null;

            response = service.getRouteId(route).execute();


            Object event = new Error(response.message());

            if (response.isSuccessful()) {
                event = response.body();
            }
            AndroidBus.getInstance().post(event);
        } catch (IOException e) {
            AndroidBus.getInstance().post(e);
        }
    }
}
