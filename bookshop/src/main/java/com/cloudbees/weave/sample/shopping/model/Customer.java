package com.cloudbees.weave.sample.shopping.model;

import com.cloudbees.weave.api.webhook.JSONSerializable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Customer implements JSONSerializable {

    @JsonProperty
    public String id;

    @JsonProperty
    public String firstName;

    @JsonProperty
    public String lastName;

    @JsonProperty
    public String email;

    @Override
    public String toString() {
        return id + ", " + firstName + " " + lastName + ", " + email;
    }

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected error serializing Customer object to JSON String.", e);
        }
    }

    public static Customer newCustomer(String id, String firstName, String lastName, String email) {
        Customer customer = new Customer();
        customer.id = id;
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.email = email;
        return customer;
    }
}
