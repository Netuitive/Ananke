package com.netuitive.ananke.statsd.entity;

import lombok.Data;
import lombok.experimental.Wither;

/**
 *
 * @author john.king
 */
@Data
@Wither
public class Tag {

    String name;
    String value;

    public Tag() {
    }
    
    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }
}
