package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.BusStop;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Manuel de la Peña
 */
public class KatangaInteractorFactoryUtilTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetBusStopsInteractor() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetBusStopsInteractor(true, true, true);

        KatangaInteractor interactor = instance.getInteractor(extras);

        Assert.assertTrue(interactor instanceof BusStopsInteractor);
    }

    @Test
    public void testGetBusStopsInteractorWithoutLatitude() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetBusStopsInteractor(true, false, true);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Main search parameters (lat, long, radio) are not present.");

        instance.getInteractor(extras);
    }

    @Test
    public void testGetBusStopsInteractorWithoutLongitude() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetBusStopsInteractor(true, true, false);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Main search parameters (lat, long, radio) are not present.");

        instance.getInteractor(extras);
    }

    @Test
    public void testGetBusStopsInteractorWithoutRadio() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetBusStopsInteractor(false, true, true);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Main search parameters (lat, long, radio) are not present.");

        instance.getInteractor(extras);
    }

    @Test
    public void testGetFavoritesInteractor() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetFavoritesInteractor(true, true);

        Mockito.when(extras.getSerializable("busStop"))
            .thenReturn(new BusStop("", "P001", "", 0, 0, ""));

        KatangaInteractor interactor = instance.getInteractor(extras);

        Assert.assertTrue(interactor instanceof FavoritesInteractor);
    }

    @Test
    public void testGetFavoritesInteractorWithFalseFavoritesFlag() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        Mockito.when(extras.containsKey("favorites")).thenReturn(true);
        Mockito.when(extras.getBoolean("favorites")).thenReturn(false);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Main search parameters (lat, long, radio) are not present.");

        instance.getInteractor(extras);
    }

    @Test
    public void testGetFavoritesInteractorWithoutFavorites() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetFavoritesInteractor(false, false);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Main search parameters (lat, long, radio) are not present.");

        instance.getInteractor(extras);
    }

    @Test
    public void testGetFavoritesInteractorWithNullBusStop() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetFavoritesInteractor(true, true);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Favorite Bus Stop is not present.");

        instance.getInteractor(extras);
    }


    @Test
    public void testGetFavoritesInteractorWithoutBusStop() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        mockGetFavoritesInteractor(true, false);

        exception.expect(InvalidInteractorException.class);
        exception.expectMessage("Favorite Bus Stop is not present.");

        instance.getInteractor(extras);
    }

    private void mockGetBusStopsInteractor(boolean radio, boolean latitude, boolean longitude) {
        Mockito.when(extras.containsKey("favorites")).thenReturn(false);

        Mockito.when(extras.containsKey("radio")).thenReturn(radio);
        Mockito.when(extras.containsKey("latitude")).thenReturn(latitude);
        Mockito.when(extras.containsKey("longitude")).thenReturn(longitude);
    }

    private void mockGetFavoritesInteractor(boolean favorites, boolean busStop) {
        Mockito.when(extras.containsKey("favorites")).thenReturn(favorites);
        Mockito.when(extras.getBoolean("favorites")).thenReturn(favorites);

        Mockito.when(extras.containsKey("busStop")).thenReturn(busStop);
    }

    @Mock
    private Bundle extras = Mockito.mock(Bundle.class);

}
