package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;
import lombok.Data;
import lombok.experimental.Wither;

/**
 *
 * @author john.king
 */
@Data
@Wither
public class GaugeRequest {

    String metric;
    Long value;
    List<Tag> tags;
    Long sampleRate;

    public GaugeRequest() {
    }
    

    public GaugeRequest(String metric, Long value, List<Tag> tags, Long sampleRate) {
        this.metric = metric;
        this.value = value;
        this.tags = tags;
        this.sampleRate = sampleRate;
    }
    
    
}