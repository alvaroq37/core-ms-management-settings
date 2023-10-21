package core.ms.management.settings.rest;

import core.ms.management.settings.impl.SubClassificationImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/subclassification")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubClassificationRest {
    @Inject
    SubClassificationImpl subClassificationImpl;

    @GET
    @Path("/list/all")
    public Response subClassificationListAll(){
        return subClassificationImpl.subClassificationListAll();
    }

    @POST
    @Path("/save")
    public Response subClassificationSave(JsonObject jsonDataSubClassification){
        return subClassificationImpl.subClassificationSave(jsonDataSubClassification);
    }
}
