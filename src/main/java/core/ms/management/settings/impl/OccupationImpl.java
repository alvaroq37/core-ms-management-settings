package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Occupation;
import core.ms.management.settings.dao.repository.OccupationRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class OccupationImpl {
    @Inject
    OccupationRepository occupationRepository;
    JsonObject jsonResponseFail = new JsonObject();
    ObjectMapper mapper = new ObjectMapper();
    public Response occupationListAll() {
        try {
            List<Occupation> occupationListAll = occupationRepository.occupationListAll();
            JsonArray jsonArrayListAll = new JsonArray(occupationListAll);
            Response response = Response.ok(jsonArrayListAll).build();

            if (response.getStatus() == 200) {
                if (occupationListAll.isEmpty()) {
                    jsonResponseFail.put("message", "LIST OCCUPATIONS EMPTY");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response occupationFindById(JsonObject jsonDataCountry) {
        try {
            long id = Long.parseLong(jsonDataCountry.getString("id"));
            Occupation occupation = occupationRepository.occupationFindById(id);
            if(occupation == null){
                jsonResponseFail.put("message", "OCCUPATION  BY ID: " + id + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArrayOccupationId = new JsonObject(mapper.writeValueAsString(occupation));
            Response response = Response.ok(jsonArrayOccupationId).build();
            if (response.getStatus() == 200) {
                if (jsonArrayOccupationId.isEmpty()) {
                    jsonResponseFail.put("message", "OCCUPATION  BY ID: " + id + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response occupationFindByName(JsonObject jsonDataCountry) {
        try {
            String description = jsonDataCountry.getString("description");
            Occupation occupation = occupationRepository.occupationFindByName(description);
            if(occupation == null){
                jsonResponseFail.put("message", "OCCUPATION  BY NAME: " + description.toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArrayCountryName = new JsonObject(mapper.writeValueAsString(occupation));
            Response response = Response.ok(jsonArrayCountryName).build();
            if (response.getStatus() == 200) {
                if (jsonArrayCountryName.isEmpty()) {
                    jsonResponseFail.put("message", "OCCUPATION  BY NAME: " + description.toUpperCase() + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response occupationSave(JsonObject jsonDataCountry) {
        try {
            Occupation occupation = new Occupation();
            occupation.setDescription(jsonDataCountry.getString("description"));
            occupationRepository.occupationSave(occupation);
            JsonObject jsonResponseOccupationSave = new JsonObject();
            jsonResponseOccupationSave.put("message", "OCCUPATION " + jsonDataCountry.getString("description").toUpperCase() + " CREATED");
            return Response.ok(jsonResponseOccupationSave).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response occupationDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseCountryDelete = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = occupationRepository.occupationDelete(id);

            if (responseDelete <= 0) {
                jsonResponseFail.put("message", "OCCUPATION " + jsonDataCity.getString("description").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            jsonResponseCountryDelete.put("message", "OCCUPATION " + jsonDataCity.getString("description").toUpperCase() + " DELETE");
            return Response.ok(jsonResponseCountryDelete).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response occupationUpdate(JsonObject jsonDataCountry) {
        try {
            JsonObject jsonResponseOccupationUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataCountry.getString("id"));
            String description = jsonDataCountry.getString("description");
            if (id <= 0 || description == null) {
                jsonResponseFail.put("message", "OCCUPATION " + jsonDataCountry.getString("description").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            Occupation occupation = occupationRepository.occupationFindById(id);
            occupation.setDescription(description);
            jsonResponseOccupationUpdate.put("message", "OCCUPATION " + description.toUpperCase() + " HAS UPDATE");
            Response response = Response.ok(jsonResponseOccupationUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
