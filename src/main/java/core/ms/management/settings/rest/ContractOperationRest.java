package core.ms.management.settings.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.ms.management.settings.impl.ContractOperationImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/contract/operation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContractOperationRest {

    @Inject
    ContractOperationImpl contractOperationImpl;


    @GET
    @Path("/find/all")
    public Response listAllContractOperation(){
        return contractOperationImpl.listAllContractOperation();
    }

    @POST
    @Path("/find/by/id")
    public Response listContractOperationById(JsonObject jsonDataOperation){
        return contractOperationImpl.listContractOperationById(jsonDataOperation);
    }

    @POST
    @Path("/find/by/id/contract")
    public Response listContractOperationByIdContract(JsonObject jsonDataOperation){
        return contractOperationImpl.listContractOperationByIdContract(jsonDataOperation);
    }

    @POST
    @Path("/search/contracts")
    public Response searchContracts(JsonObject jsonData) throws JsonProcessingException {
        return contractOperationImpl.searchContracts(jsonData);
    }

    @POST
    @Path("/save")
    public Response saveContractOperation(JsonObject jsonDataOperation){
        return contractOperationImpl.saveContractOperation(jsonDataOperation);
    }

    @PUT
    @Path("/update")
    public Response updateContractOperation(JsonObject jsonDataOperation){
        return contractOperationImpl.updateContractOperation(jsonDataOperation);
    }

    @DELETE
    @Path("/delete")
    public Response deleteContractOperation(JsonObject jsonDataOperation){
        return contractOperationImpl.deleteContractOperation(jsonDataOperation);
    }
}
