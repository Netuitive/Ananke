package com.netuitive.ananke.statsd.client;

import com.netuitive.ananke.statsd.client.exception.EventException;
import com.netuitive.ananke.statsd.client.request.DecrementRequest;
import com.netuitive.ananke.statsd.client.request.EventRequest;
import com.netuitive.ananke.statsd.client.request.GaugeRequest;
import com.netuitive.ananke.statsd.client.request.HistorgramRequest;
import com.netuitive.ananke.statsd.client.request.IncrementRequest;
import com.netuitive.ananke.statsd.client.request.ServiceCheckRequest;
import com.netuitive.ananke.statsd.client.request.SetRequest;
import com.netuitive.ananke.statsd.client.request.TimedRequest;
import com.netuitive.ananke.statsd.client.request.TimingRequest;
import com.netuitive.ananke.statsd.entity.Tag;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author john.king
 */
public class NetuitiveStatsDClient implements StatsDClient{

    DatagramSocket socket;
    InetAddress address;
    int port;
    public final Long MAX_MESSAGE_SIZE=8L * 1024L;
    
    public NetuitiveStatsDClient(String hostname, int port) throws SocketException, UnknownHostException{
        this(InetAddress.getByName(hostname), port);
    }
    
    public NetuitiveStatsDClient(InetAddress address, int port) throws SocketException{
        this.socket = new DatagramSocket();
        this.address = address;
        this.port = port;
    }
    @Override
    public void decrement(DecrementRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "c", req.getValue()*-1,req.getTags(), req.getSampleRate()));
    }

    @Override
    public void event(EventRequest req) throws IOException{
        String message = formatEvent(req);
        if(message.length() > MAX_MESSAGE_SIZE){
            throw new EventException("message size of " + message.length() + " is greater than max message size of " + MAX_MESSAGE_SIZE);
        }
        send(message);
    }

    @Override
    public void gauge(GaugeRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "g", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void histogram(HistorgramRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "h", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void increment(IncrementRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "c", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    /*
     Send a service check run.
        >>> statsd.service_check('my_service.check_name', DogStatsd.WARNING)
        """
        message = self._escape_service_check_message(message) if message is not None else ''

        string = u'_sc|{0}|{1}'.format(check_name, status)

        if timestamp:
            string = u'{0}|d:{1}'.format(string, timestamp)
        if hostname:
            string = u'{0}|h:{1}'.format(string, hostname)
        if tags:
            string = u'{0}|#{1}'.format(string, ','.join(tags))
        if message:
            string = u'{0}|m:{1}'.format(string, message)

        try:
            self.socket.send(string.encode(self.encoding))
    
    _sc|netuitive-agent|1
    */
    private String formatStatusCheck(ServiceCheckRequest req){
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("_sc|%s|%d", req.getCheckName(), req.getStatus().ordinal());
        if(req.getTimestamp() != null){
            formatter.format("%s|d:%d", req.getTimestamp().getTime());
        }
        if(req.getHostname()!=null && !req.getHostname().isEmpty()){
            formatter.format("%s|h:%s", 
                    builder.toString(), 
                    req.getHostname());
        }
        if(req.getTags()!= null & !req.getTags().isEmpty()){
            formatter.format("%s|#:%s", 
                    builder.toString(), 
                    formatTags(req.getTags()));
        }
        if(req.getMessage()!=null && !req.getMessage().isEmpty()){
            formatter.format("%s|h:%s", 
                    builder.toString(), 
                    req.getMessage());
        }
        return builder.toString();
    }
    @Override
    public void serviceCheck(ServiceCheckRequest req) throws IOException{
        send(formatStatusCheck(req));
    }

    @Override
    public void set(SetRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "s", req.getValue(),req.getTags(), req.getSampleRate()));
    }

    @Override
    public void timed(TimedRequest req) throws IOException{
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
    public void timing(TimingRequest req) throws IOException{
        send(formatMetric(req.getMetric(), "ms", req.getValue(),req.getTags(), req.getSampleRate()));
    }
    /*_e{15,4}:polls index hit|text
    string = u'_e{%d,%d}:%s|%s' % (len(title), len(text), title, text)
        if date_happened:
            string = '%s|d:%d' % (string, date_happened)
        if hostname:
            string = '%s|h:%s' % (string, hostname)
        if aggregation_key:
            string = '%s|k:%s' % (string, aggregation_key)
        if priority:
            string = '%s|p:%s' % (string, priority)
        if source_type_name:
            string = '%s|s:%s' % (string, source_type_name)
        if alert_type:
            string = '%s|t:%s' % (string, alert_type)
        if tags:
            string = '%s|#%s' % (string, ','.join(tags))

        if len(string) > 8 * 1024:
            raise Exception(u'Event "%s" payload is too big (more that 8KB), '
                            'event discarded' % title)

        try:
            self.socket.send(string.encode(self.encoding))*/
    private String formatEvent(EventRequest req){
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("_e{%d,%d}:%s|%s", 
                req.getTitle().length(),
                req.getTitle().length(),
                req.getTitle(),
                req.getText());
        if(req.getDateHappened() != null){
            formatter.format("%s|d:%d", 
                    builder.toString(), 
                    req.getDateHappened().getTime());
        }
        if(req.getHostname()!=null && !req.getHostname().isEmpty()){
            formatter.format("%s|h:%s", 
                    builder.toString(), 
                    req.getHostname());
        }
        if(req.getAggregationKey() != null & !req.getAggregationKey().isEmpty()){
            formatter.format("%s|k:%s", 
                    builder.toString(), 
                    req.getAggregationKey());
        }
        if(req.getPriority() != null & !req.getPriority().isEmpty()){
            formatter.format("%s|p:%s", 
                    builder.toString(), 
                    req.getPriority());
        }
        if(req.getSourceTypeName()!= null & !req.getSourceTypeName().isEmpty()){
            formatter.format("%s|s:%s", 
                    builder.toString(), 
                    req.getSourceTypeName());
        }
        if(req.getAlertType()!= null & !req.getAlertType().isEmpty()){
            formatter.format("%s|t:%s", 
                    builder.toString(), 
                    req.getAlertType());
        }
        if(req.getTags()!= null & !req.getTags().isEmpty()){
            formatter.format("%s|#:%s", 
                    builder.toString(), 
                    formatTags(req.getTags()));
        }
        return builder.toString();
    }
    /*
      payload = [metric, ":", value, "|", metric_type]
        if sample_rate != 1:
            payload.extend(["|@", sample_rate])
        if tags:
            payload.extend(["|#", ",".join(tags)])

    */
    private String formatMetric(String metric, String metricType, Long value, List<Tag> tags, Long sampleRate){
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%s:%d|%s", metric, value, metricType);
        if(sampleRate != null && sampleRate != 1L){
            formatter.format("%s|@%d", builder.toString(), sampleRate);
        }
        if(tags != null && !tags.isEmpty()){
            formatter.format("%s|#%s", builder.toString(), formatTags(tags));
        }
        return builder.toString();
    }
    
    private String formatTags(List<Tag> tags){
        StringBuilder builder = new StringBuilder();
        if(tags != null && !tags.isEmpty()){
            tags.forEach(tag -> {
                builder.append(tag.getName())
                        .append(":")
                        .append(tag.getValue())
                        .append(",");
            });
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
    
    private void send(String message) throws IOException {
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
                address, port);
        socket.send(packet);
    }

}
