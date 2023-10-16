package core.ms.management.settings.rest;

import core.ms.management.settings.impl.CountryImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
@Path("rest/country")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryRest {

    @Inject
    CountryImpl countryImpl;

    @GET
    @Path("/find/all")
    public Response countryListAll() {
        return countryImpl.countryListAll();
    }

    @GET
    @Path("/find/by/id")
    public Response countryFindById(JsonObject jsonCountry) {
        return countryImpl.countryFindById(jsonCountry);
    }

    @GET
    @Path("/find/by/name")
    public Response countryFindByName(JsonObject jsonCountry) {
        return countryImpl.countryFindByName(jsonCountry);
    }

    @POST
    @Path("/save")
    public Response countrySave(JsonObject jsonCountry) {
        return countryImpl.countrySave(jsonCountry);
    }

    @PUT
    @Path("/update")
    public Response countryUpdate(JsonObject jsonCountry) {
        return countryImpl.countryUpdate(jsonCountry);
    }

    @DELETE
    @Path("/delete")
    public Response countryDelete(JsonObject jsonCountry) {
        return countryImpl.countryDelete(jsonCountry);
    }
}
