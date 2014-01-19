package es.kgp.chat.server.exception;

/**
 * Created by kgp on 19/01/2014.
 */
public class GenericErrorResponse {

    private final String error;

    public GenericErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
