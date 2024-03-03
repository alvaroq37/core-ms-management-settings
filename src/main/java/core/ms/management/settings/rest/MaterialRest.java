package core.ms.management.settings.rest;

import core.ms.management.settings.impl.MaterialImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/material")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MaterialRest {

    @Inject
    MaterialImpl materialImpl;

    @POST
    @Path("/save")
    public Response materialSave(JsonObject jsonDataClassification) {
        return materialImpl.materialSave(jsonDataClassification);
    }

    @GET
    @Path("/find/all")
    public Response materialListAll() {
        return materialImpl.materialListAll();
    }
}