package com.netuitive.ananke.statsd.client;

import com.netuitive.ananke.statsd.client.exception.EventException;
import com.netuitive.ananke.statsd.client.exception.MetricException;
import com.netuitive.ananke.statsd.client.exception.ServiceCheckException;
import com.netuitive.ananke.statsd.client.request.DecrementRequest;
import com.netuitive.ananke.statsd.client.request.EventRequest;
import com.netuitive.ananke.statsd.client.request.GaugeRequest;
import com.netuitive.ananke.statsd.client.request.HistogramRequest;
import com.netuitive.ananke.statsd.client.request.IncrementRequest;
import com.netuitive.ananke.statsd.client.request.ServiceCheckRequest;
import com.netuitive.ananke.statsd.client.request.SetRequest;
import com.netuitive.ananke.statsd.client.request.TimedRequest;
import com.netuitive.ananke.statsd.client.request.TimingRequest;
import com.netuitive.ananke.statsd.entity.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author john.king
 */
public class NetuitiveStatsDClient implements StatsDClient {

    private static Logger LOG = LoggerFactory.getLogger(NetuitiveStatsDClient.class);

    DatagramSocket socket;
    String host;
    int port;

    public static final Long MAX_MESSAGE_SIZE = 8L * 1024L;
    
    public NetuitiveStatsDClient(String host, int port) throws SocketException {
        this(new DatagramSocket(), host, port);
    }
    
    public NetuitiveStatsDClient(DatagramSocket socket, String host, int port) {
        this.socket = socket;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void decrement(DecrementRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "c", req.getValue()*-1,req.getTags(), req.getSampleRate()));
    }

    @Override
    public void event(EventRequest req) throws IllegalArgumentException {
        String message = formatEvent(req);
        if(message.length() > MAX_MESSAGE_SIZE){
            throw new EventException("message size of " + message.length() + " is greater than max message size of " + MAX_MESSAGE_SIZE);
        }
        send(message);
    }

    @Override
    public void gauge(GaugeRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "g", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void histogram(HistogramRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "h", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void increment(IncrementRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "c", req.getValue(),req.getTags(), req.getSampleRate()));
    }
    protected String formatServiceCheck(ServiceCheckRequest req) throws IllegalArgumentException {
        if(req.getCheckName() == null || req.getCheckName().isEmpty() || req.getStatus() == null){
            throw new ServiceCheckException("checkName and status are required fields");
        }
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("_sc|%s|%d", req.getCheckName(), req.getStatus().ordinal());
        if(req.getTimestamp() != null){
            formatter.format("|d:%d", req.getTimestamp().getTime());
        }
        if(req.getHostname()!=null && !req.getHostname().isEmpty()){
            formatter.format("|h:%s", 
                    req.getHostname());
        }
        if(req.getTags()!= null && !req.getTags().isEmpty()){
            formatter.format("|#%s", 
                    formatTags(req.getTags()));
        }
        if(req.getMessage()!=null && !req.getMessage().isEmpty()){
            formatter.format("|m:%s", 
                    req.getMessage());
        }
        return builder.toString();
    }
    @Override
    public void serviceCheck(ServiceCheckRequest req) throws IllegalArgumentException {
        send(formatServiceCheck(req));
    }

    @Override
    public void set(SetRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "s", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void timed(TimedRequest req) throws IllegalArgumentException {
        if(req.getFunc() == null){
            throw new MetricException("Func is a required field");
        }
        Date start = new Date();
        try {
            req.getFunc().call();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        Date end = new Date();
        timing(new TimingRequest()
                .withMetric(req.getMetric())
                .withSampleRate(req.getSampleRate())
                .withTags(req.getTags())
                .withValue(end.getTime()-start.getTime()));
    }

    @Override
    public void timing(TimingRequest req) throws IllegalArgumentException {
        send(formatMetric(req.getMetric(), "ms", req.getValue(),req.getTags(), req.getSampleRate()));
    }
    
    protected String formatEvent(EventRequest req) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        if(req.getTitle() == null || req.getTitle().isEmpty()){
            throw new EventException("title is a required field");
        }
        if(req.getText() == null){
            req.setText("");
        }
        formatter.format("_e{%d,%d}:%s|%s", 
                req.getTitle().length(),
                req.getText().length(),
                req.getTitle(),
                req.getText());
        if(req.getDateHappened() != null){
            formatter.format("|d:%d",  
                    req.getDateHappened().getTime());
        }
        if(req.getHostname()!=null && !req.getHostname().isEmpty()){
            formatter.format("|h:%s", 
                    req.getHostname());
        }
        if(req.getAggregationKey() != null & !req.getAggregationKey().isEmpty()){
            formatter.format("|k:%s",  
                    req.getAggregationKey());
        }
        if(req.getPriority() != null & !req.getPriority().isEmpty()){
            formatter.format("|p:%s", 
                    req.getPriority());
        }
        if(req.getSourceTypeName()!= null & !req.getSourceTypeName().isEmpty()){
            formatter.format("|s:%s", 
                    req.getSourceTypeName());
        }
        if(req.getAlertType()!= null & !req.getAlertType().isEmpty()){
            formatter.format("|t:%s", 
                    req.getAlertType());
        }
        if(req.getTags()!= null & !req.getTags().isEmpty()){
            formatter.format("|#%s", 
                    formatTags(req.getTags()));
        }
        return builder.toString();
    }
    
    protected String formatMetric(String metric, String metricType, Long value, List<Tag> tags, Long sampleRate) throws IllegalArgumentException {
        if(metric == null || metric.isEmpty() || value == null){
            throw new MetricException("metric and value are required fields");
        }
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%s:%d|%s", metric, value, metricType);
        
        if(sampleRate != null && sampleRate != 1L){
            formatter.format("|@%d", sampleRate);
        }
        if(tags != null && !tags.isEmpty()){
            formatter.format("|#%s", formatTags(tags));
        }
        return builder.toString();
    }
    
    protected String formatTags(List<Tag> tags) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();
        if(tags != null && !tags.isEmpty()){
            for(Tag tag : tags){
                if (tag == null) {
                    throw new IllegalArgumentException("StatsD tags cannot be null");
                }
                builder.append(tag.getName())
                        .append(":")
                        .append(tag.getValue())
                        .append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
    
    protected void send(String message){
        byte[] buf = message.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(this.host), port);
            socket.send(packet);
        } catch (IOException e) {
            LOG.debug("Unable to connect to StatsD host: {}", this.host, e);
        }
    }
}
