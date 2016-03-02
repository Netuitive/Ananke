package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;

/**
 *
 * @author john.king
 */
public class DecrementRequest extends AbstractMetricRequest {

    public DecrementRequest() {
    }

    public DecrementRequest(String metric, Long value, List<Tag> tags, Long sampleRate) {
        super(metric, value, tags, sampleRate);
    }

    public DecrementRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new DecrementRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public DecrementRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new DecrementRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public DecrementRequest withValue(Long value) {
        return this.value == value ? this : new DecrementRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public DecrementRequest withMetric(String metric) {
        return this.metric == metric ? this : new DecrementRequest(metric, this.value, this.tags, this.sampleRate);
    }

}
