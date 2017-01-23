package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;


/**
 *
 * @author john.king
 */
public class HistogramRequest extends AbstractMetricRequest{
    public HistogramRequest() {
    }

    public HistogramRequest(String metric, Double value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }

    public HistogramRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new HistogramRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public HistogramRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new HistogramRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public HistogramRequest withValue(Long value) {
    	Double dvalue = (value == null ? null : value.doubleValue());
        return this.value == dvalue ? this : new HistogramRequest(this.metric, dvalue, this.tags, this.sampleRate);
    }

    public HistogramRequest withValue(Double value) {
        return this.value == value ? this : new HistogramRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public HistogramRequest withMetric(String metric) {
        return this.metric == metric ? this : new HistogramRequest(metric, this.value, this.tags, this.sampleRate);
    }
}
