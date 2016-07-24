package es.craftsmanship.toledo.katangapp.interactors;

import android.os.Bundle;

import es.craftsmanship.toledo.katangapp.models.BusStop;

/**
 * @author Manuel de la Peña
 */
public class KatangaInteractorFactoryUtil implements KatangaInteractorFactory {

    public static KatangaInteractorFactoryUtil getInstance() {
        return instance;
    }

    @Override
    public KatangaInteractor getInteractor(Bundle extras) {
        boolean favorites = extras.getBoolean("favorites");

        if (favorites) {
            BusStop busStop = (BusStop) extras.getSerializable("busStop");

            FavoritesInteractor favoritesInteractor = new FavoritesInteractor(
                busStop.getId());

            return favoritesInteractor;
        }
        else {
            String radio = extras.getString("radio");
            Double latitude = extras.getDouble("latitude");
            Double longitude = extras.getDouble("longitude");

            BusStopsInteractor busStopsInteractor = new BusStopsInteractor(
                radio, latitude, longitude);

            return busStopsInteractor;
        }
    }

    private KatangaInteractorFactoryUtil() {
    }

    private static KatangaInteractorFactoryUtil instance = new KatangaInteractorFactoryUtil();
}