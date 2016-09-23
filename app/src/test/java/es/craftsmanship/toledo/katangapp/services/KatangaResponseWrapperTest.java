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
        KatangaResponseWrapper<Integer> katangaResponseWrapper = new KatangaResponseWrapper<>(1000);

        Integer response = katangaResponseWrapper.getResponse();

        Assert.assertTrue(response != null);
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

        Assert.assertTrue(response != null);
        Assert.assertEquals(1, response.getResults().size());
    }

    @Test
    public void testConstructorFromString() {
        KatangaResponseWrapper<String> katangaResponseWrapper = new KatangaResponseWrapper<>(
            "Hello World!");

        String response = katangaResponseWrapper.getResponse();

        Assert.assertTrue(response != null);
        Assert.assertEquals("Hello World!", response);
    }

}