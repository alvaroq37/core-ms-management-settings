package core.ms.management.settings.rest;

import core.ms.management.settings.impl.JewelImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/jewel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class JewelRest {

    @Inject
    JewelImpl jewelImpl;

    @GET
    @Path("/find/all")
    public Response jewelListAll(){
        return jewelImpl.jewelListAll();
    }

    @POST
    @Path("/find/by/id")
    public Response jewelFindByCi(JsonObject jsonDataJewel){

        return jewelImpl.jewelFindById(jsonDataJewel);
    }

    @POST
    @Path("/save")
    public Response jewelSave(JsonObject jsonDataJewel){

        return jewelImpl.jewelSave(jsonDataJewel);
    }
}

