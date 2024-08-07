package core.ms.management.settings.rest;

import core.ms.management.settings.impl.UserImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/user")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {

    @Inject
    UserImpl userImpl;

    @GET
    @Path("/find/all")
    public Response userFindAll() {
        return userImpl.userListAll();
    }

    @POST
    @Path("/find/by/id")
    public Response userFindById(JsonObject jsonDataUser){
        return userImpl.userFindById(jsonDataUser);
    }

    @POST
    @Path("/find/by/name")
    public Response userFindByName(JsonObject jsonDataUser){
        return userImpl.userFindByName(jsonDataUser);
    }

    @POST
    @Path("/save")
    public Response userSave(JsonObject jsonDataUser) {
        return userImpl.userSave(jsonDataUser);
    }

    @PUT
    @Path("/update")
    public Response userUpdate(JsonObject jsonDataUser) {
        return userImpl.userUpdate(jsonDataUser);
    }

    @DELETE
    @Path("/delete")
    public Response userDelete(JsonObject jsonDataUser) {
        return userImpl.userDelete(jsonDataUser);
    }
}
