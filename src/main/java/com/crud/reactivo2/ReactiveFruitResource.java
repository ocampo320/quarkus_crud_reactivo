package com.crud.reactivo2;

import io.smallrye.mutiny.Uni;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/reactive_fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReactiveFruitResource {

    @Inject
    ReactiveFruitService fruitService;

    @GET
    public Uni<List<Fruit>> list() {
        return fruitService.list();
    }

    @POST
    public Uni<List<Fruit>> add(Fruit fruit) {
        return fruitService.add(fruit)
                .onItem().ignore().andSwitchTo(this::list);
    }
    @DELETE
    public Uni<Void>delete(String id){
        return fruitService.delete();
    }
}
