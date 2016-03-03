package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.Data;
import lombok.experimental.Wither;

/**
 *
 * @author john.king
 */
@Data
@Wither
public class TimedRequest {

    String metric;
    List<Tag> tags;
    Long sampleRate;
    Boolean useMs;
    Callable func;

    public TimedRequest() {
    }

    public TimedRequest(String metric, List<Tag> tags, Long sampleRate, Boolean useMs, Callable func) {
        this.metric = metric;
        this.tags = tags;
        this.sampleRate = sampleRate;
        this.useMs = useMs;
        this.func = func;
    }
    
    
}
