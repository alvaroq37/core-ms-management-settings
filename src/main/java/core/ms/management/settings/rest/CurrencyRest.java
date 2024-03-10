package core.ms.management.settings.rest;

import core.ms.management.settings.impl.CurrencyImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/currency")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyRest {

    @Inject
    CurrencyImpl currencyImpl;

    @GET
    @Path("/find/all")
    public Response currencyListAll() {
        return currencyImpl.currencyListAll();
    }

    @POST
    @Path("/save")
    public Response currencySave(JsonObject jsonCurrency) {
        return currencyImpl.currencySave(jsonCurrency);
    }

    @PUT
    @Path("/update")
    public Response currencyUpdate(JsonObject jsonCurrency) {
        return currencyImpl.currencyUpdate(jsonCurrency);
    }

    @POST
    @Path("/delete")
    public Response currencyDelete(JsonObject jsonCurrency) {
        return currencyImpl.currencyDelete(jsonCurrency);
    }
}
