package com.cloudbees.weave.sample.shopping.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Response {

    public static final Response SUCCESS = new Response("success");

    public Response() {}

    public Response(String status) {
        this.status = status;
    }

    @JsonProperty
    public String status;

    @JsonProperty
    public String message;

    @JsonProperty
    public List<? extends Object> data;

    public static Response success(String message) {
        return response("success", message);
    }

    public static Response fail(String message) {
        return response("error", message);
    }

    private static Response response(String status, String message) {
        Response response = new Response(status);
        response.message = message;
        return response;
    }
}
