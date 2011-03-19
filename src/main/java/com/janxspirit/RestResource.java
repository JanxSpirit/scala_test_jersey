package com.janxspirit;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class RestResource {

    private List beerList;

    public RestResource() {
        beerList = new ArrayList();
        beerList.add("Corona");
        beerList.add("Guiness");
        beerList.add("Fat Tire");
    }

    @GET
    @Produces("application/json")
    @Path("beers/{name}")
    public Response getBeer(@PathParam("name") String name, @QueryParam("sizeOunces") int sizeOunces) {
        if (name == null || !beerList.contains(name)) {
            return Response.status(Status.NOT_FOUND).entity(String.format("Sorry we don't have any %s", name)).build();
        }
        if (sizeOunces < 8) {
            return Response.status(Status.BAD_REQUEST).entity("Sorry no kid-size beers.").build();
        }
        Beer beer = new Beer(name, sizeOunces);
        System.out.println(String.format("Pouring %s ounces of %s", sizeOunces, name));
        return Response.ok(beer).build();
    }
}
