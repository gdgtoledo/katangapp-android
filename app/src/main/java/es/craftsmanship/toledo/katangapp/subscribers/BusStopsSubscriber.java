package es.craftsmanship.toledo.katangapp.subscribers;

import com.squareup.otto.Subscribe;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

/**
 * @author Manuel de la Peña
 */
public interface BusStopsSubscriber {

    @Subscribe
    void busStopsReceived(Error error);

    @Subscribe
    void busStopsReceived(QueryResult queryResult);

}