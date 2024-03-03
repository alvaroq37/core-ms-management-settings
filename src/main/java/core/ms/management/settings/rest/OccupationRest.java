package core.ms.management.settings.rest;

import core.ms.management.settings.impl.OccupationImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/occupation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OccupationRest {

    @Inject
    OccupationImpl occupationImpl;

    @GET
    @Path("/find/all")
    public Response occupationListAll() {
        return occupationImpl.occupationListAll();
    }

    @GET
    @Path("/find/by/id")
    public Response occupationFindById(JsonObject jsonOccupation) {
        return occupationImpl.occupationFindById(jsonOccupation);
    }

    @GET
    @Path("/find/by/description")
    public Response occupationFindByName(JsonObject jsonOccupation) {
        return occupationImpl.occupationFindByName(jsonOccupation);
    }

    @POST
    @Path("/save")
    public Response occupationSave(JsonObject jsonOccupation) {
        return occupationImpl.occupationSave(jsonOccupation);
    }

    @PUT
    @Path("/update")
    public Response occupationUpdate(JsonObject jsonOccupation) {
        return occupationImpl.occupationUpdate(jsonOccupation);
    }

    @DELETE
    @Path("/delete")
    public Response occupationDelete(JsonObject jsonOccupation) {
        return occupationImpl.occupationDelete(jsonOccupation);
    }
}
