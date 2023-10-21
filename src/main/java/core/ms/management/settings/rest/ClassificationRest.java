package core.ms.management.settings.rest;

import core.ms.management.settings.impl.ClassificationImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/classification")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClassificationRest {

    @Inject
    ClassificationImpl classificationImpl;

    @POST
    @Path("/save")
    public Response classificationSave(JsonObject jsonDataClassification){
        return classificationImpl.classificationSave(jsonDataClassification);
    }

    @GET
    @Path("/list/all")
    public Response classificationListAll(){
        return classificationImpl.classificationListAll();
    }
}
