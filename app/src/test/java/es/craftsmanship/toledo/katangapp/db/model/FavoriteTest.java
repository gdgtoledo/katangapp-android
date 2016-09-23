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