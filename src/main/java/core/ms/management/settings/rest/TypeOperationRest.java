package core.ms.management.settings.rest;

import core.ms.management.settings.impl.TypeOperationImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/type/operation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TypeOperationRest {

    @Inject
    TypeOperationImpl typeOperationImpl;

    @POST
    @Path("/save")
    public Response saveTypeOperation(JsonObject jsonData){
        return typeOperationImpl.typeOperationSave(jsonData);
    }

    @PUT
    @Path("/update")
    public Response updateTypeOperation(JsonObject jsonData){
        return typeOperationImpl.typeOperationUpdate(jsonData);
    }

    @DELETE
    @Path("/delete")
    public Response deleteTypeOperation(JsonObject jsonData){
        return typeOperationImpl.typeOperationDelete(jsonData);
    }
}
