package es.craftsmanship.toledo.katangapp.services;

/**
 * @author Manuel de la Peña
 */
public class KatangaResponseWrapper<R> {

    private R response;

    public KatangaResponseWrapper(R response) {
        this.response = response;
    }

    public R getResponse() {
        return response;
    }

}