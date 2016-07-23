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