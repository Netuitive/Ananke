package com.netuitive.ananke.statsd.client.exception;

/**
 *
 * @author john.king
 */
public class ClientException extends RuntimeException{

    public ClientException(Throwable cause) {
        super(cause);
    }
    
    public ClientException(String message) {
        super(message);
    }
}
