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
import java.io.IOException;

/**
 *
 * @author john.king
 */
public interface StatsDClient {
    public void decrement(DecrementRequest req) throws IOException;
    
    public void event(EventRequest req) throws IOException;
    
    public void gauge(GaugeRequest req) throws IOException;
    
    public void histogram(HistogramRequest req) throws IOException;
    
    public void increment(IncrementRequest req) throws IOException;
    
    public void serviceCheck(ServiceCheckRequest req) throws IOException;
    
    public void set(SetRequest req) throws IOException;
    
    public void timed(TimedRequest req) throws IOException;
    
    public void timing(TimingRequest req) throws IOException;
}
