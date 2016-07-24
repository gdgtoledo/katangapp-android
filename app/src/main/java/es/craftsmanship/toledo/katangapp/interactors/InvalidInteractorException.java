package es.craftsmanship.toledo.katangapp.interactors;

/**
 * @author Manuel de la Peña
 */
public class InvalidInteractorException extends Exception {

    public InvalidInteractorException() {
        this("The interactor was not properly configured.");
    }

    public InvalidInteractorException(String message) {
        super(message);
    }
}