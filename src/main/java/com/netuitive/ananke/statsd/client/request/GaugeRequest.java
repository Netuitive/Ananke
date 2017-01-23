package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;

/**
 *
 * @author john.king
 */
public class GaugeRequest extends AbstractMetricRequest{
    public GaugeRequest() {
    }

    public GaugeRequest(String metric, Double value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }

    public GaugeRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new GaugeRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public GaugeRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new GaugeRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public GaugeRequest withValue(Long value) {
        Double dvalue = (value == null ? null : value.doubleValue());
        return this.value == dvalue ? this : new GaugeRequest(this.metric, dvalue, this.tags, this.sampleRate);
    }

    public GaugeRequest withValue(Double value) {
        return this.value == value ? this : new GaugeRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public GaugeRequest withMetric(String metric) {
        return this.metric == metric ? this : new GaugeRequest(metric, this.value, this.tags, this.sampleRate);
    }
}
