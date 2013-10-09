package com.cloudbees.weave.sample.shopping.model;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class CustomerTest {

    @Test
    public void test() {
        Customer contact = new Customer();
        contact.email = "t@x.com";

        Assert.assertEquals("{\"id\":null,\"firstName\":null,\"lastName\":null,\"email\":\"t@x.com\"}", contact.toJSON());
    }
}
