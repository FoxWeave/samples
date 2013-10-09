package com.cloudbees.weave.sample.shopping;

import com.cloudbees.weave.api.webhook.WEAVEHook;
import com.cloudbees.weave.sample.shopping.model.Response;
import com.cloudbees.weave.sample.shopping.model.Shipment;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/api/shipment")
@Produces("application/json")
@Consumes("application/json")
public class ShipmentService {

    /**
     * {@link WEAVEHook} is a utility class that helps your RUN@cloud App to manage
     * notification Webhooks installed by a WEAVE@cloud integration.
     */
    private WEAVEHook shipmentWeaveHook = new WEAVEHook("shipment");

    @POST
    public Response shipProduct(Shipment shipment) {
        //
        // Do whatever your RUN@cloud app needs to do to process the shipment...
        //

        System.out.println("\n**************************************************************************");
        System.out.println("Goods shipped. Sending WEAVE@cloud notification: ");
        System.out.println("**************************************************************************\n");

        // Notify all WEAVE@cloud integrations that the shipment has been processed...
        shipmentWeaveHook.created(shipment);

        return Response.SUCCESS;
    }

    /**
     * Add a "shipment" Webhook.
     * <p/>
     * Called by a WEAVE@cloud integration to register its "interest" in
     * receiving notifications from this RUN@cloud App when a product "shipment"
     * takes place.
     * <p/>
     * Multiple webhooks can be added i.e. multiple WEAVE@cloud integrations
     * are interested in knowing about shipment events.
     *
     * @param webhookDirective The Webhook directive.
     */
    @POST
    @Path("/webhook")
    public Response addWebhook(String webhookDirective) {
        shipmentWeaveHook.addWebhook(webhookDirective);
        return Response.SUCCESS;
    }

    /**
     * Remove a "shipment" Webhook.
     * <p/>
     * Called by a WEAVE@cloud integration to unregister its "interest" in
     * receiving notifications from this RUN@cloud App when a product "shipment"
     * takes place.
     *
     * @param id The id of the webhook to be removed.
     */
    @DELETE
    @Path("/webhook/{id}")
    public Response removeWebhook(@PathParam("id") String id) {
        shipmentWeaveHook.removeWebhook(id);
        return Response.SUCCESS;
    }
}