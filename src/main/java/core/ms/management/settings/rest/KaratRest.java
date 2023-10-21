package core.ms.management.settings.rest;

import core.ms.management.settings.impl.KaratImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/karat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KaratRest {

    @Inject
    KaratImpl karatImpl;

    @GET
    @Path("/list/all")
    public Response karatListAll(){
        return karatImpl.karatListAll();
    }

    @POST
    @Path("/save")
    public Response karatSave(JsonObject jsonDataKarat){
        return karatImpl.karatSave(jsonDataKarat);
    }
}
