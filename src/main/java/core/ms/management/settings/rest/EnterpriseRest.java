package core.ms.management.settings.rest;

import core.ms.management.settings.impl.EnterpriseImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/enterprise")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnterpriseRest {

    @Inject
    EnterpriseImpl enterpriseImpl;

    @Path("/list/all")
    @GET
    public Response enterpriseListAll(){
        return enterpriseImpl.enterpriseListAll();
    }

    @Path("/find/by/id")
    @POST
    public Response enterpriseFindById(JsonObject jsonDataEnterprise){
        return enterpriseImpl.enterpriseFindById(jsonDataEnterprise);
    }

    @Path("/save")
    @POST
    public Response enterpriseSave(JsonObject jsonDataEnterprise){
        return enterpriseImpl.enterpriseSave(jsonDataEnterprise);
    }
}
