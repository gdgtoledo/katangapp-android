package es.craftsmanship.toledo.katangapp.db.model;

/**
 * @author Manuel de la Peña
 */
public class Favorite {

    private String busStopId;
    private long id;

    public String getBusStopId() {
        return busStopId;
    }

    public long getId() {
        return id;
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
        return busStopId;
    }

}