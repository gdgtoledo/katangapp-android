/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.interactors;

import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.utils.ExtrasConstants;

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

        Mockito.when(extras.getSerializable(ExtrasConstants.BUS_STOP))
            .thenReturn(new BusStop("", "P001", "", 0, 0, ""));

        KatangaInteractor interactor = instance.getInteractor(extras);

        Assert.assertTrue(interactor instanceof FavoritesInteractor);
    }

    @Test
    public void testGetFavoritesInteractorWithFalseFavoritesFlag() throws Exception {
        KatangaInteractorFactoryUtil instance = KatangaInteractorFactoryUtil.getInstance();

        Mockito.when(extras.containsKey(ExtrasConstants.ACTIVITY_FAVORITES)).thenReturn(true);
        Mockito.when(extras.getBoolean(ExtrasConstants.ACTIVITY_FAVORITES)).thenReturn(false);

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
        Mockito.when(extras.containsKey(ExtrasConstants.ACTIVITY_FAVORITES)).thenReturn(false);

        Mockito.when(extras.containsKey(ExtrasConstants.RADIO)).thenReturn(radio);
        Mockito.when(extras.containsKey(ExtrasConstants.LATITUDE)).thenReturn(latitude);
        Mockito.when(extras.containsKey(ExtrasConstants.LONGITUDE)).thenReturn(longitude);
    }

    private void mockGetFavoritesInteractor(boolean favorites, boolean busStop) {
        Mockito.when(extras.containsKey(ExtrasConstants.ACTIVITY_FAVORITES)).thenReturn(favorites);
        Mockito.when(extras.getBoolean(ExtrasConstants.ACTIVITY_FAVORITES)).thenReturn(favorites);

        Mockito.when(extras.containsKey(ExtrasConstants.BUS_STOP)).thenReturn(busStop);
    }

    @Mock
    private Bundle extras = Mockito.mock(Bundle.class);

}
