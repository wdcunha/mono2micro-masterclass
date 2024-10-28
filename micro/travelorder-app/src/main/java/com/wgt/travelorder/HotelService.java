package com.wgt.travelorder;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.temporal.ChronoUnit;

@RegisterRestClient(baseUri = "http://localhost:8082/hotel")
public interface HotelService {

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findById(@QueryParam("id") long id);

    @GET
    @Path("findByTravelOrderId")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(unit = ChronoUnit.SECONDS, value = 2)
    @Fallback(fallbackMethod = "fallback")
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public Hotel findByTravelOrderId(@QueryParam("travelOrderId") long travelOrderId);

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Hotel newHotel(Hotel hotel);

    default Hotel fallback(long travelOrderId) {
        Hotel hotel= new Hotel();
        hotel.setNights(-1); // front can have a logic to treat when it is -1, because trip can be just coming back
        // at the same day
        hotel.setTravelOrderId(travelOrderId);
        return new Hotel();
    }

}
