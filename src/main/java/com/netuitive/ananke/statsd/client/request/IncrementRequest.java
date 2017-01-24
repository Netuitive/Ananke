package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;


/**
 *
 * @author john.king
 */
public class IncrementRequest extends AbstractMetricRequest{
    public IncrementRequest() {
    }

    public IncrementRequest(String metric, Long value, List<Tag> tags, Long sampleRate) {
    	this(metric, new Double(value), tags, sampleRate);
    }
    
    public IncrementRequest(String metric, Double value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }

    public IncrementRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new IncrementRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public IncrementRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new IncrementRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public IncrementRequest withValue(Long value) {
        Double dvalue = (value == null ? null : value.doubleValue());
        return this.value == dvalue ? this : new IncrementRequest(this.metric, dvalue, this.tags, this.sampleRate);
    }

    public IncrementRequest withValue(Double value) {
        return this.value == value ? this : new IncrementRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public IncrementRequest withMetric(String metric) {
        return this.metric == metric ? this : new IncrementRequest(metric, this.value, this.tags, this.sampleRate);
    }
}
