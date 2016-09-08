package es.craftsmanship.toledo.katangapp.interactors;

import android.os.Bundle;

import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.utils.ExtrasConstants;

/**
 * @author Manuel de la Peña
 */
public class KatangaInteractorFactoryUtil implements KatangaInteractorFactory {

    public static KatangaInteractorFactoryUtil getInstance() {
        return instance;
    }

    @Override
    public KatangaInteractor getInteractor(Bundle extras) throws InvalidInteractorException {
        validate(extras);

        boolean favorites = extras.getBoolean(ExtrasConstants.ACTIVITY_FAVORITES);

        if (favorites) {
            BusStop busStop = (BusStop) extras.getSerializable(ExtrasConstants.BUS_STOP);

            FavoritesInteractor favoritesInteractor = new FavoritesInteractor(
                busStop.getId());

            return favoritesInteractor;
        }
        else {
            String radio = extras.getString("radio");
            Double latitude = extras.getDouble(ExtrasConstants.LATITUDE);
            Double longitude = extras.getDouble("longitude");

            BusStopsInteractor busStopsInteractor = new BusStopsInteractor(
                radio, latitude, longitude);

            return busStopsInteractor;
        }
    }

    private KatangaInteractorFactoryUtil() {
    }

    /**
     * If a <code>favorites</code> flag is present and is true, then it will validate that a busStop
     * is present.
     *
     * If a <code>favorites</code> flag is not present, or it's present with <code>false</code> value,
     * then it will validate that the parameters to query the main service are present.
     *
     * @param extras the extras values for the Android intent
     * @throws InvalidInteractorException if any validation condition is not satisfied
     */
    private void validate(Bundle extras) throws InvalidInteractorException {
        if (extras.containsKey(ExtrasConstants.ACTIVITY_FAVORITES) &&
            extras.getBoolean(ExtrasConstants.ACTIVITY_FAVORITES)) {

            if (!extras.containsKey(ExtrasConstants.BUS_STOP) ||
                (extras.getSerializable(ExtrasConstants.BUS_STOP) == null)) {

                throw new InvalidInteractorException("Favorite Bus Stop is not present.");
            }

            return;
        }

        if (!extras.containsKey("radio") || !extras.containsKey(ExtrasConstants.LATITUDE) ||
            !extras.containsKey("longitude")) {

            throw new InvalidInteractorException(
                "Main search parameters (lat, long, radio) are not present.");
        }
    }

    private static KatangaInteractorFactoryUtil instance = new KatangaInteractorFactoryUtil();

}