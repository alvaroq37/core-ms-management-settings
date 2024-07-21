package core.ms.management.settings.rest;


import core.ms.management.settings.impl.LoanTypeImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/loan/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class LoanTypeRest {

    @Inject
    LoanTypeImpl loanTypeImpl;

    @GET
    @Path("/list/all")
    public Response listAllLoanType(){
        return loanTypeImpl.listAllLoanType();
    }

    @POST
    @Path("/save")
    public Response saveLoanType(JsonObject jsonDataLoanType){
        return loanTypeImpl.saveLoanType(jsonDataLoanType);
    }

    @POST
    @Path("/find/by/id")
    public Response findByIdLoanType(JsonObject jsonDataLoanType){
        return loanTypeImpl.findByIdLoanType(jsonDataLoanType);
    }
}
