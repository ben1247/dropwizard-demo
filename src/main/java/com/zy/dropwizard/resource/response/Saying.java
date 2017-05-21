package com.zy.dropwizard.resource.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
public class Saying {

    private long id;

    @Length(max = 3)
    private String content;

    public Saying(){

    }

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

}