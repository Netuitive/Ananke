package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Tag;
import java.util.List;
import lombok.Data;

/**
 *
 * @author john.king
 */
@Data
public abstract class AbstractMetricRequest {

    String metric;
    Long value;
    List<Tag> tags;
    Long sampleRate;
}
