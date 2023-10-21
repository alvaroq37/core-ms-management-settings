package core.ms.management.settings.rest;

import core.ms.management.settings.impl.MaterialPriceImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/material/price")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MaterialPriceRest {

    @Inject
    MaterialPriceImpl materialPriceImpl;

    @POST
    @Path("/save")
    public Response materialPriceSave(JsonObject jsonDataClassification) {
        return materialPriceImpl.materialPriceSave(jsonDataClassification);
    }

    @GET
    @Path("/list/all")
    public Response materialPriceListAll() {
        return materialPriceImpl.materialPriceListAll();
    }
}
