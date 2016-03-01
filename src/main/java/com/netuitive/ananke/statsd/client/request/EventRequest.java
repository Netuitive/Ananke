package com.netuitive.ananke.statsd.client.request;

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
public class EventRequest {

    String title;
    String text;
    String alertType;
    String aggregationKey;
    String sourceTypeName;
    Date dateHappened; 
    String priority;
    List<Tag> tags;
    String hostname;

    public EventRequest() {
    }
    

    public EventRequest(String title, String text, String alertType, String aggregationKey, String sourceTypeName, Date dateHappened, String priority, List<Tag> tags, String hostname) {
        this.title = title;
        this.text = text;
        this.alertType = alertType;
        this.aggregationKey = aggregationKey;
        this.sourceTypeName = sourceTypeName;
        this.dateHappened = dateHappened;
        this.priority = priority;
        this.tags = tags;
        this.hostname = hostname;
    }
    
}
