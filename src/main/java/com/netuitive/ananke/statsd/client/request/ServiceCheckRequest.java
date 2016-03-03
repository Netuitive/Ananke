package com.netuitive.ananke.statsd.client.request;

import com.netuitive.ananke.statsd.entity.Status;
import com.netuitive.ananke.statsd.entity.Tag;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Wither;

/**
 *
 * @author john.king
 */
@Data
@Wither
public class ServiceCheckRequest {

    String checkName;
    Status status;
    List<Tag> tags;
    Date timestamp;
    String hostname;
    String message;

    public ServiceCheckRequest() {
    }

    public ServiceCheckRequest(String checkName, Status status, List<Tag> tags, Date timestamp, String hostname, String message) {
        this.checkName = checkName;
        this.status = status;
        this.tags = tags;
        this.timestamp = timestamp;
        this.hostname = hostname;
        this.message = message;
    }
    
    
}
