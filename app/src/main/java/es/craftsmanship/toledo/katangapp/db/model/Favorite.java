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

/**
 * @author Manuel de la Peña
 */
public class Favorite {

    private String address;
    private String busStopId;
    private long id;

    public Favorite() {}

    public Favorite(BusStop busStop) {
        busStopId = busStop.getId();
        address = busStop.getAddress();
    }

    public String getAddress() {
        return address;
    }

    public String getBusStopId() {
        return busStopId;
    }

    public long getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusStopId(String busStopId) {
        this.busStopId = busStopId;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Will be used by the ArrayAdapter in the ListView
     */
    @Override
    public String toString() {
        return busStopId + " - " + address;
    }

}