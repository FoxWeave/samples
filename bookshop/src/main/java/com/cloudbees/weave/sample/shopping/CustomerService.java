package com.cloudbees.weave.sample.shopping;

import com.cloudbees.weave.sample.shopping.model.Customer;
import com.cloudbees.weave.sample.shopping.model.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;


@Path("/api/customer")
@Produces("application/json")
@Consumes("application/json")
public class CustomerService {

    private static List<Customer> customerDatabase = new ArrayList<Customer>();

    /**
     * Receive a "customer" CREATED event from a WEAVE@cloud integration.
     * @param customer The customer JSON bound into a Customer object.
     */
    @POST
    @Path("/created")
    public void created(Customer customer) {

        System.out.println("\n**************************************************************************");
        System.out.println("Customer added to CustomerService: [" + customer + "].");
        System.out.println("**************************************************************************\n");

        customerDatabase.add(customer);
    }

    /**
     * Get a list of all customers.
     * @return All customers response.
     */
    @GET
    public Response getAll() {
        Response response = Response.success(null);
        response.data = customerDatabase;
        return response;
    }

    static {
        customerDatabase.add(Customer.newCustomer("1", "Winston", "Churchill", "wc@weave.com"));
        customerDatabase.add(Customer.newCustomer("2", "Charles", "de Gaulle", "cdg@weave.com"));
        customerDatabase.add(Customer.newCustomer("3", "Franklin D", "Roosevelt", "wc@weave.com"));
    }
}