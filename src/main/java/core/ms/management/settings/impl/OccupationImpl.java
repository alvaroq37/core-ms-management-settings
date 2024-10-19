package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Occupation;
import core.ms.management.settings.dao.repository.OccupationRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OccupationImpl {
    @Inject
    OccupationRepository occupationRepository;
    JsonObject jsonResponse = new JsonObject();
    ObjectMapper mapper = new ObjectMapper();
    public Response occupationListAll() {
        try {
            List<Occupation> occupationListAll = occupationRepository.occupationListAll();
            JsonArray jsonArrayListAll = new JsonArray(occupationListAll);
            Response response = Response.ok(jsonArrayListAll).build();

            if (response.getStatus() == 200) {
                if (occupationListAll.isEmpty()) {
                    jsonResponse.put("message", "No existen ocupaciones registradas");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
            return Response.ok(jsonResponse.put("message","Servicio no disponible")).build();
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response occupationFindById(JsonObject jsonDataCountry) {
        try {
            long id = Long.parseLong(jsonDataCountry.getString("id"));
            Occupation occupation = occupationRepository.occupationFindById(id);
            if(occupation == null){
                jsonResponse.put("message", "Ocupación: " + id + " no registrada");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArrayOccupationId = new JsonObject(mapper.writeValueAsString(occupation));
            Response response = Response.ok(jsonArrayOccupationId).build();
            if (response.getStatus() == 200) {
                if (jsonArrayOccupationId.isEmpty()) {
                    jsonResponse.put("message", "Ocupación: " + id + " no registrada");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
            return Response.ok(jsonResponse.put("message","Servicio no disponible")).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response occupationFindByName(JsonObject jsonDataCountry) {
        try {
            String description = jsonDataCountry.getString("description");
            Occupation occupation = occupationRepository.occupationFindByName(description);
            if(occupation == null){
                jsonResponse.put("message", "Ocupación: " + description + " no registrada");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArrayCountryName = new JsonObject(mapper.writeValueAsString(occupation));
            Response response = Response.ok(jsonArrayCountryName).build();
            if (response.getStatus() == 200) {
                if (jsonArrayCountryName.isEmpty()) {
                    jsonResponse.put("message", "Ocupación: " + description + " no registrada");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
            return Response.ok(jsonResponse.put("message","Servicio no disponible")).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response occupationSave(JsonObject jsonDataCountry) {
        try {
            Occupation occupation = new Occupation();
            occupation.description = jsonDataCountry.getString("description");
            occupation.dateCreate = new Date();
            occupationRepository.occupationSave(occupation);
            JsonObject jsonResponseOccupationSave = new JsonObject();
            jsonResponseOccupationSave.put("message", "Ocupación " + jsonDataCountry.getString("description") + " registrada correctamente");
            return Response.ok(jsonResponseOccupationSave).build();
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response occupationDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseCountryDelete = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = occupationRepository.occupationDelete(id);

            if (responseDelete <= 0) {
                jsonResponse.put("message", "Ocupación " + jsonDataCity.getString("description") + " no registrada");
                return Response.ok(jsonResponse).build();
            }
            jsonResponseCountryDelete.put("message", "Ocupación " + jsonDataCity.getString("description") + " eliminada correctamente");
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
                jsonResponse.put("message", "Ocupación " + jsonDataCountry.getString("description") + " no registrada");
                return Response.ok(jsonResponse).build();
            }
            Occupation occupation = occupationRepository.occupationFindById(id);
            occupation.description = description;
            jsonResponseOccupationUpdate.put("message", "Ocupación " + description.toUpperCase() + " actualizada correctamente");
            Response response = Response.ok(jsonResponseOccupationUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
