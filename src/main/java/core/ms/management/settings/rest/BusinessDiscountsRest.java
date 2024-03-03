package core.ms.management.settings.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.ms.management.settings.impl.BusinessDiscountsImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rest/business/discount")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusinessDiscountsRest {

    @Inject
    BusinessDiscountsImpl businessDiscountsImpl;

    @GET
    @Path("/find/all")
    public Response cityFindAll() throws JsonProcessingException {
        return businessDiscountsImpl.businessDiscountListAll();
    }

    @POST
    @Path("/save")
    public Response citySave(JsonObject jsonBusinessDiscount) {
        return businessDiscountsImpl.saveBusinessDiscount(jsonBusinessDiscount);
    }
}
