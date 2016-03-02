package com.netuitive.ananke.statsd.client.exception;

/**
 *
 * @author john.king
 */
public class MetricException extends RuntimeException{

    public MetricException(String message){
        super(message);
    }
}
