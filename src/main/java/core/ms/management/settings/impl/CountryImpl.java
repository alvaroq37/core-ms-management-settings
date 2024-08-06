package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Country;
import core.ms.management.settings.dao.repository.CountryRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CountryImpl {

    @Inject
    CountryRepository countryRepository;

    JsonObject jsonResponseFail = new JsonObject();

    ObjectMapper mapper = new ObjectMapper();
    public Response countryListAll() {
        try {
            List<Country> countryListAll = countryRepository.countryListAll();
            JsonArray jsonArrayListAll = new JsonArray(countryListAll);
            Response response = Response.ok(jsonArrayListAll).build();

            if (response.getStatus() == 200) {
                if (countryListAll.isEmpty()) {
                    jsonResponseFail.put("message", "No existen paises registrados");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponseFail.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response countryFindById(JsonObject jsonDataCountry) {
        try {
            long id = Long.parseLong(jsonDataCountry.getString("id"));
            Country country = countryRepository.countryFindById(id);
            if(country == null){
                jsonResponseFail.put("message", "País con Id: " + id + " no se encuentra registrado");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArrayCountry = new JsonObject(mapper.writeValueAsString(country));
            Response response = Response.ok(jsonArrayCountry).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponseFail.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response countryFindByName(JsonObject jsonDataCountry) {
        try {
            String name = jsonDataCountry.getString("name");
            Country country = countryRepository.countryFindByName(name);
            if(country == null){
                jsonResponseFail.put("message", "País: " + name + " no se encuentra registrado");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArrayCountry = new JsonObject(mapper.writeValueAsString(country));
            Response response = Response.ok(jsonArrayCountry).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponseFail.put("message", e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response countrySave(JsonObject jsonDataCountry) {
        try {
            JsonObject jsonResponseCountrySave = new JsonObject();
            Country country = new Country();
            country.name = (jsonDataCountry.getString("name"));
            country.dateCreate = new Date();
            country.userCreate = 0;
            if(!country.name.isEmpty()){
                countryRepository.countrySave(country);
                jsonResponseCountrySave.put("message", "País " + jsonDataCountry.getString("name") + " registrado");
            }else{
                jsonResponseCountrySave.put("message", "No se ingresó el nombre del país");
            }
            return Response.ok(jsonResponseCountrySave).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response countryDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseCountryDelete = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = countryRepository.countryDelete(id);

            if (responseDelete <= 0) {
                jsonResponseFail.put("message", "País " + jsonDataCity.getString("name") + " no se encuentra registrado");
                return Response.ok(jsonResponseFail).build();
            }
            jsonResponseCountryDelete.put("message", "País: " + jsonDataCity.getString("name") + " ha sido eliminado correctamente");
            return Response.ok(jsonResponseCountryDelete).build();
        } catch (Exception e) {
            return Response.ok(jsonResponseFail.put("message", e.getMessage())).build();
        }
    }

    public Response countryUpdate(JsonObject jsonDataCountry) {
        try {
            JsonObject jsonResponseCountryUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataCountry.getString("id"));
            String name = jsonDataCountry.getString("name");
            if (id <= 0 || name == null) {
                jsonResponseFail.put("message", "País " + jsonDataCountry.getString("name") + " no está registrado");
                return Response.ok(jsonResponseFail).build();
            }
            Country country = countryRepository.countryFindById(id);
            country.name = jsonDataCountry.getString("name");
            country.dateUpdate = new Date();
            countryRepository.countryUpdate(country);
            jsonResponseCountryUpdate.put("message", "País " + name + " actualizado correctamente");
            Response response = Response.ok(jsonResponseCountryUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(jsonResponseFail.put("message", e.getMessage())).build();
        }
    }
}
