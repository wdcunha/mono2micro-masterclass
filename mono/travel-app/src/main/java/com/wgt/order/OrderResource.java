package com.wgt.order;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("order")
public class OrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> orders() {
        return Order.listAll();
    }
}