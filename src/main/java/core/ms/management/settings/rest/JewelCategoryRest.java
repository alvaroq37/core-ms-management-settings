package core.ms.management.settings.rest;

import core.ms.management.settings.impl.JewelCategoryImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("/rest/jewel/category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class JewelCategoryRest {

    @Inject
    JewelCategoryImpl jewelCategoryImpl;

    @GET
    @Path("/list/all")
    public Response jewelCategoryListAll(){
        return jewelCategoryImpl.jewelCategoryListAll();
    }

    @POST
    @Path("/save")
    public Response jewelCategorySave(JsonObject jsonDataJewelCategory){
        return jewelCategoryImpl.jewelCategorySave(jsonDataJewelCategory);
    }
}
