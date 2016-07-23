package es.craftsmanship.toledo.katangapp.db.model;

import es.craftsmanship.toledo.katangapp.models.BusStop;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class FavoriteTest {

    @Test
    public void testConstructorFromBusStop() {
        BusStop busStop = new BusStop("L92", "P001", "1.00000", 0, 0, "Plaza de Zocodover, 1");

        Favorite favorite = new Favorite(busStop);

        Assert.assertEquals(busStop.getId(), favorite.getBusStopId());
        Assert.assertEquals(busStop.getAddress(), favorite.getAddress());
    }

}