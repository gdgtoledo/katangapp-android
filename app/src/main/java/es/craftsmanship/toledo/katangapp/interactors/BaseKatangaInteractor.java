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

import es.craftsmanship.toledo.katangapp.services.BusStopsService;
import es.craftsmanship.toledo.katangapp.services.KatangaResponseWrapper;
import es.craftsmanship.toledo.katangapp.utils.AndroidBus;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseKatangaInteractor implements KatangaInteractor {

    private static final String BACKEND_ENDPOINT = "https://secret-depths-4660.herokuapp.com";

    @Override
    public void run() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            BusStopsService service = retrofit.create(BusStopsService.class);

            KatangaResponseWrapper katangaResponseWrapper = getResponse(service);

            Response response = (Response) katangaResponseWrapper.getResponse();

            Object event = new Error(response.message());

            if (response.isSuccessful()) {
                event = response.body();
            }

            AndroidBus.getInstance().post(event);
        }
        catch (IOException e) {
            AndroidBus.getInstance().post(e);
        }
    }

    protected abstract KatangaResponseWrapper getResponse(BusStopsService busStopsService)
        throws IOException;

}