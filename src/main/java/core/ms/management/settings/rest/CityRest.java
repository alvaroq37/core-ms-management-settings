package core.ms.management.settings.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import core.ms.management.settings.impl.CityImpl;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/rest/city")
@Transactional
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CityRest {
    @Inject
    CityImpl cityImpl;

    @GET
    @Path("/find/all")
    public Response cityFindAll() throws JsonProcessingException {
        return cityImpl.cityListAll();
    }

    @GET
    @Path("/find/by/id")
    public Response cityFindById(JsonObject jsonCity) throws JsonProcessingException {

        return cityImpl.cityFindById(jsonCity);
    }

    @POST
    @Path("/find/by/name")
    public Response cityFindByName(JsonObject jsonCity) throws JsonProcessingException {

        return cityImpl.cityFindByName(jsonCity);
    }

    @POST
    @Path("/save")
    public Response citySave(JsonObject jsonCity) {
        return cityImpl.citySave(jsonCity);
    }

    @PUT
    @Path("/edit")
    public Response cityUpdate(JsonObject jsonCity) {
        return cityImpl.cityUpdate(jsonCity);
    }

    @DELETE
    @Path("/delete")
    public Response cityDelete(JsonObject jsonCity) {
        return cityImpl.cityDelete(jsonCity);
    }
}
