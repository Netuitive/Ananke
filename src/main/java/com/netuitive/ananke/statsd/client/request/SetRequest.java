package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;


/**
 *
 * @author john.king
 */
public class SetRequest extends AbstractMetricRequest{
    public SetRequest() {
    }

    public SetRequest(String metric, Long value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }

    public SetRequest withSampleRate(Long sampleRate) {
        return this.sampleRate == sampleRate ? this : new SetRequest(this.metric, this.value, this.tags, sampleRate);
    }

    public SetRequest withTags(List<Tag> tags) {
        return this.tags == tags ? this : new SetRequest(this.metric, this.value, tags, this.sampleRate);
    }

    public SetRequest withValue(Long value) {
        return this.value == value ? this : new SetRequest(this.metric, value, this.tags, this.sampleRate);
    }

    public SetRequest withMetric(String metric) {
        return this.metric == metric ? this : new SetRequest(metric, this.value, this.tags, this.sampleRate);
    }
}
