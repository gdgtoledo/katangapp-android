package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Javier Gamarra
 */
public class BusStopsInteractor implements Runnable {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";
    private final String radio;
    private final Double latitude;
    private final Double longitude;

    public BusStopsInteractor(String radio, Double latitude, Double longitude) {
        this.radio = radio;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            BusStopsService service = retrofit.create(BusStopsService.class);

            Response<QueryResult> response = service.listBusStops(
                latitude, longitude, radio).execute();

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