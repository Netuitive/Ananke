package com.netuitive.ananke.statsd.client.exception;

/**
 *
 * @author john.king
 */
public class ServiceCheckException extends RuntimeException{

    public ServiceCheckException(String message){
        super(message);
    }
}
