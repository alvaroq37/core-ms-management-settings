package core.ms.management.settings.rest;

import core.ms.management.settings.impl.ClientCategoryImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/client/category")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientCategoryRest {

    @Inject
    ClientCategoryImpl clientCategoryImpl;

    @GET
    @Path("/find/all")
    public Response clientCategoryListAll(){
        return clientCategoryImpl.clientCategoryListAll();
    }

    @GET
    @Path("/find/id")
    public Response clientCategoryFindById(JsonObject jsonDataClientCategory){
        return clientCategoryImpl.clientCategoryFindById(jsonDataClientCategory);
    }

    @GET
    @Path("/find/description")
    public Response clientCategoryFindByDescription(JsonObject jsonDataClientCategory) {
        return clientCategoryImpl.clientCategoryFindByDescription(jsonDataClientCategory);
    }

    @POST
    @Path("/save")
    public Response clientCategorySave(JsonObject jsonDataClientCategory){
        return clientCategoryImpl.clientCategorySave(jsonDataClientCategory);
    }

    @DELETE
    @Path("/delete")
    public Response clientCategoryDelete(JsonObject jsonDataClientCategory){
        return clientCategoryImpl.clientCategoryDelete(jsonDataClientCategory);
    }

    @PUT
    @Path("/update")
    public Response clientCategoryUpdate(JsonObject jsonDataClientCategory){
        return clientCategoryImpl.clientCategoryUpdate(jsonDataClientCategory);
    }
}
