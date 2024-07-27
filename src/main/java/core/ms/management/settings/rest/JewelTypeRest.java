package core.ms.management.settings.rest;

import core.ms.management.settings.impl.JewelTypeImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/jewel/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class JewelTypeRest {

    @Inject
    JewelTypeImpl jewelTypeImpl;

    @GET
    @Path("/find/all")
    public Response jewelListAll(){
        return jewelTypeImpl.listAllJewelType();
    }

    @POST
    @Path("/find/by/id")
    public Response jewelFindByCi(JsonObject jsonDataJewelType){

        return jewelTypeImpl.findByIdJewelType(jsonDataJewelType);
    }

    @POST
    @Path("/save")
    public Response jewelSave(JsonObject jsonDataJewelType){

        return jewelTypeImpl.saveJewelType(jsonDataJewelType);
    }
}
