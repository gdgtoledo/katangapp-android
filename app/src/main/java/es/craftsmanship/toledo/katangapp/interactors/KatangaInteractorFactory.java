package es.craftsmanship.toledo.katangapp.interactors;

import android.os.Bundle;

/**
 * @author Manuel de la Peña
 */
public interface KatangaInteractorFactory {

    KatangaInteractor getInteractor(final Bundle extras);

}