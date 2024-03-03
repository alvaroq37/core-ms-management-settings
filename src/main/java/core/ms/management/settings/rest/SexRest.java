package core.ms.management.settings.rest;

import core.ms.management.settings.impl.SexImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("rest/sex")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SexRest {

    @Inject
    SexImpl sexImpl;

    @GET
    @Path("/find/all")
    public Response sexListAll() {
        return sexImpl.SexListAll();
    }

    @GET
    @Path("/find/by/id")
    public Response sexFindById(JsonObject jsonSex) {
        return sexImpl.SexFindById(jsonSex);
    }

    @GET
    @Path("/find/by/description")
    public Response sexFindByDescription(JsonObject jsonSex) {
        return sexImpl.SexFindByDescription(jsonSex);
    }

    @POST
    @Path("/save")
    public Response sexSave(JsonObject jsonSex) {
        return sexImpl.citySave(jsonSex);
    }

    @PUT
    @Path("/update")
    public Response sexUpdate(JsonObject jsonSex) {
        return sexImpl.SexUpdate(jsonSex);
    }

    @DELETE
    @Path("/delete")
    public Response sexDelete(JsonObject jsonSex) {
        return sexImpl.SexDelete(jsonSex);
    }
}
