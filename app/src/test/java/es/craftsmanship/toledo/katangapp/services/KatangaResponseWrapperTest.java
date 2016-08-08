package es.craftsmanship.toledo.katangapp.services;

import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class KatangaResponseWrapperTest {

    @Test
    public void testConstructorFromInteger() {
        KatangaResponseWrapper<Integer> katangaResponseWrapper = new KatangaResponseWrapper<>(
            new Integer(1000));

        Integer response = katangaResponseWrapper.getResponse();

        Assert.assertTrue(response instanceof Integer);
        Assert.assertEquals(1000, response.intValue());
    }

    @Test
    public void testConstructorFromObject() {
        List<BusStopResult> busStopResults = new ArrayList<>();

        busStopResults.add(new BusStopResult());

        QueryResult queryResult = new QueryResult(busStopResults);

        KatangaResponseWrapper<QueryResult> katangaResponseWrapper = new KatangaResponseWrapper<>(
            queryResult);

        QueryResult response = katangaResponseWrapper.getResponse();

        Assert.assertTrue(response instanceof QueryResult);
        Assert.assertEquals(1, response.getResults().size());
    }

    @Test
    public void testConstructorFromString() {
        KatangaResponseWrapper<String> katangaResponseWrapper = new KatangaResponseWrapper<>(
            "Hello World!");

        String response = katangaResponseWrapper.getResponse();

        Assert.assertTrue(response instanceof String);
        Assert.assertEquals("Hello World!", response);
    }

}