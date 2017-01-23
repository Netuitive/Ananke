package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;

/**
 *
 * @author john.king
 */
public class TimingRequest extends AbstractMetricRequest{ 
    public TimingRequest() {
    }
    
    public TimingRequest(String metric, Double value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }

    public TimingRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new TimingRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public TimingRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new TimingRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public TimingRequest withValue(Long value) {
        Double dvalue = (value == null ? null : value.doubleValue());
        return this.value == dvalue ? this : new TimingRequest(this.metric, dvalue, this.tags, this.sampleRate);
    }

    public TimingRequest withValue(Double value) {
        return this.value == value ? this : new TimingRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public TimingRequest withMetric(String metric) {
        return this.metric == metric ? this : new TimingRequest(metric, this.value, this.tags, this.sampleRate);
    }
}
