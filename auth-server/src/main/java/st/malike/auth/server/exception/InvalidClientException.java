/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.exception;

/**
 *
 * @author malike_st
 */
public class InvalidClientException extends Exception {

    public InvalidClientException() {
    }

    public InvalidClientException(String message) {
        super(message);
    }

    public InvalidClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidClientException(Throwable cause) {
        super(cause);
    }

    public InvalidClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
