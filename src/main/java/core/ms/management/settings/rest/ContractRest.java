package core.ms.management.settings.rest;

import core.ms.management.settings.impl.ContractImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/contract")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContractRest {

    @Inject
    ContractImpl contractImpl;

    @GET
    @Path("/find/all")
    public Response clientCategoryListAll(){

        return contractImpl.contractListAll();
    }

    @POST
    @Path("/find/id")
    public Response contractFindById(JsonObject jsonDataContract){
        return contractImpl.contractFindById(jsonDataContract);
    }

    @POST
    @Path("/save")
    public Response contractSave(JsonObject jsonDataContract){

        return contractImpl.contractSave(jsonDataContract);
    }

    @DELETE
    @Path("/delete")
    public Response contractDelete(JsonObject jsonDataContract){
        return contractImpl.contractDelete(jsonDataContract);
    }

    @PUT
    @Path("/update")
    public Response contractUpdate(JsonObject jsonDataContract){
        return contractImpl.contractUpdate(jsonDataContract);
    }
}
