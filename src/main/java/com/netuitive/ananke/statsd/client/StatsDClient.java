package com.netuitive.ananke.statsd.client;

import com.netuitive.ananke.statsd.client.request.DecrementRequest;
import com.netuitive.ananke.statsd.client.request.EventRequest;
import com.netuitive.ananke.statsd.client.request.GaugeRequest;
import com.netuitive.ananke.statsd.client.request.HistogramRequest;
import com.netuitive.ananke.statsd.client.request.IncrementRequest;
import com.netuitive.ananke.statsd.client.request.ServiceCheckRequest;
import com.netuitive.ananke.statsd.client.request.SetRequest;
import com.netuitive.ananke.statsd.client.request.TimedRequest;
import com.netuitive.ananke.statsd.client.request.TimingRequest;
import java.lang.IllegalArgumentException;

/**
 *
 * @author john.king
 */
public interface StatsDClient {
    public void decrement(DecrementRequest req) throws IllegalArgumentException;
    
    public void event(EventRequest req) throws IllegalArgumentException;
    
    public void gauge(GaugeRequest req) throws IllegalArgumentException;
    
    public void histogram(HistogramRequest req) throws IllegalArgumentException;
    
    public void increment(IncrementRequest req) throws IllegalArgumentException;
    
    public void serviceCheck(ServiceCheckRequest req) throws IllegalArgumentException;
    
    public void set(SetRequest req) throws IllegalArgumentException;
    
    public void timed(TimedRequest req) throws IllegalArgumentException;
    
    public void timing(TimingRequest req) throws IllegalArgumentException;
}
