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
import com.netuitive.ananke.statsd.entity.Status;
import com.netuitive.ananke.statsd.entity.Tag;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 *
 * @author john.king
 */
@Test(groups = "unit")
public class NetuitiveStatsDClientTest {

    
    private static final String METRIC = "test.metric";
    private static final Long VALUE = 6L;
    private static final String SAMPLE_RATE = "5";
    private static final String TAGS = "test.metric.1:test.value.1,test.metric.2:test.value.2";
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final Long DATE = 100000L;
    private static final String HOSTNAME = "hostname";
    private static final String AGGREGATION_KEY = "aggregation_key";
    private static final String PRIORITY = "priority";
    private static final String SOURCE_TYPE_NAME = "source_type_name";
    private static final String ALERT_TYPE = "alert_type";
    private static final String CHECK_NAME = "check_name";
    private static final Status STATUS = Status.OK;
    private static final String MESSAGE = "message";
    private static final String INCREMENT_FORMAT = METRIC + ":" + VALUE + "|c|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String DECREMENT_FORMAT = METRIC + ":" + (VALUE*-1) + "|c|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String GAUGE_FORMAT = METRIC + ":" + VALUE + "|g|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String HISTOGRAM_FORMAT = METRIC + ":" + VALUE + "|h|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String SET_FORMAT = METRIC + ":" + VALUE + "|s|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String TIMING_FORMAT = METRIC + ":" + VALUE + "|ms|@" + SAMPLE_RATE + "|#" + TAGS;
    private static final String EVENT_FORMAT = "_e{" + TITLE.length() + "," + TEXT.length() + "}:" 
            + TITLE + "|" + TEXT + "|d:" + DATE + "|h:" + HOSTNAME + "|k:" + AGGREGATION_KEY + "|p:" + PRIORITY + 
            "|s:" + SOURCE_TYPE_NAME + "|t:" + ALERT_TYPE + "|#" + TAGS;
    private static final String SERVICE_CHECK_FORMAT = "_sc|" + CHECK_NAME + "|" + STATUS.ordinal() + "|d:"+ DATE + 
            "|h:" + HOSTNAME + "|#" + TAGS + "|m:" + MESSAGE;
    
    
    private NetuitiveStatsDClient client;

    @Mock
    private DatagramSocket socket;
    
    @Captor
    private ArgumentCaptor<DatagramPacket> argCaptor;
    
    @BeforeClass
    private void init() throws UnknownHostException{
        MockitoAnnotations.initMocks(this);
        client = new NetuitiveStatsDClient(socket, "127.0.0.1", 8875);
        argCaptor = ArgumentCaptor.forClass(DatagramPacket.class);
        
    }
    
    @Test
    public void testIncrement() throws IOException, IllegalArgumentException {
        IncrementRequest req = new IncrementRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.increment(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), INCREMENT_FORMAT);
    }
    
    @Test
    public void testDecrement() throws IOException, IllegalArgumentException {
        DecrementRequest req = new DecrementRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.decrement(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), DECREMENT_FORMAT);
    }
    
    @Test
    public void testGauge() throws IOException, IllegalArgumentException {
        GaugeRequest req = new GaugeRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.gauge(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), GAUGE_FORMAT);
    }
    
    @Test
    public void testHistogram() throws IOException, IllegalArgumentException {
        HistogramRequest req = new HistogramRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.histogram(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), HISTOGRAM_FORMAT);
    }
    
    @Test
    public void testSet() throws IOException, IllegalArgumentException {
        SetRequest req = new SetRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.set(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), SET_FORMAT);
    }
    
    @Test
    public void testTiming() throws IOException, IllegalArgumentException {
        TimingRequest req = new TimingRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withValue(6L);
        client.timing(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), TIMING_FORMAT);
    }
    
    @Test
    public void testTimed() throws IOException, IllegalArgumentException {
        TimedRequest req = new TimedRequest()
                .withMetric("test.metric")
                .withSampleRate(5L)
                .withTags(getTags())
                .withFunc(getCallable());
        client.timed(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertTrue(new String(argCaptor.getValue().getData()).contains("|ms|"));
    }
    
    @Test
    public void testEvent() throws IOException, IllegalArgumentException {
        EventRequest req = new EventRequest()
                .withAggregationKey("aggregation_key")
                .withAlertType("alert_type")
                .withDateHappened(new Date(DATE))
                .withHostname("hostname")
                .withPriority("priority")
                .withSourceTypeName("source_type_name")
                .withTags(getTags())
                .withText("text")
                .withTitle("title");
        client.event(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        assertEquals(new String(argCaptor.getValue().getData()), EVENT_FORMAT);
    }
    @Test
    public void testServiceCheck() throws IOException, IllegalArgumentException {
        ServiceCheckRequest req = new ServiceCheckRequest()
                .withHostname("hostname")
                .withTags(getTags())
                .withCheckName("check_name")
                .withMessage("message")
                .withStatus(Status.OK)
                .withTimestamp(new Date(DATE));
        client.serviceCheck(req);
        verify(socket, atLeastOnce()).send(argCaptor.capture());
        String res = new String(argCaptor.getValue().getData());
        assertEquals(new String(argCaptor.getValue().getData()), SERVICE_CHECK_FORMAT);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void testNullFormatTags() throws IllegalArgumentException {
        List<Tag> nullTags = Arrays.asList(new Tag("Name", "Value"), null);
        client.formatTags(nullTags);
    }
    
    private List<Tag> getTags(){
        List<Tag> tags = new ArrayList<Tag>();
        for(int i = 1; i < 3; i++){
            tags.add(new Tag().withName("test.metric." + i).withValue("test.value." + i));
        }
        return tags;
    }
    
    private Callable<String> getCallable() {
        return new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "hello";
            }
        };
    }
}
