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

/**
 *
 * @author john.king
 */
public interface StatsDClient {
    public void decrement(DecrementRequest req);
    
    public void event(EventRequest req);
    
    public void gauge(GaugeRequest req);
    
    public void histogram(HistogramRequest req);
    
    public void increment(IncrementRequest req);
    
    public void serviceCheck(ServiceCheckRequest req);
    
    public void set(SetRequest req);
    
    public void timed(TimedRequest req);
    
    public void timing(TimingRequest req);
}
