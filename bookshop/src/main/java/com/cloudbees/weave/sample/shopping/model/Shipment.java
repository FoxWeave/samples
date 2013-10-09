package com.cloudbees.weave.sample.shopping.model;

import com.cloudbees.weave.api.webhook.JSONSerializable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Shipment implements JSONSerializable {

    @JsonProperty
    public String productName;

    @JsonProperty
    public Customer customer;

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected error serializing Shipment object to JSON String.", e);
        }
    }
}
