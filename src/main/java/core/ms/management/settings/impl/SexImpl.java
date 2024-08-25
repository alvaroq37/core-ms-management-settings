package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Gender;
import core.ms.management.settings.dao.repository.SexRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SexImpl {

    @Inject
    SexRepository sexRepository;

    JsonObject jsonResponse = new JsonObject();

    public Response SexListAll() {
        try {
            List<Gender> genderListAll = sexRepository.sexListAll();
            JsonArray jsonArrayListAll = new JsonArray(genderListAll);
            Response response = Response.ok(jsonArrayListAll).build();

            if (response.getStatus() == 200) {
                if (genderListAll.isEmpty()) {
                    jsonResponse.put("message", "No existen generos registrados");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response SexFindById(JsonObject jsonDataSex) {
        try {
            long id = Long.parseLong(jsonDataSex.getString("id"));
            Gender gender = sexRepository.sexFindById(id);
            if(gender == null){
                jsonResponse.put("message", "Género: " + id + " no se encuentra registrado");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArraySex = new JsonObject(Json.encode(gender));
            Response response = Response.ok(jsonArraySex).build();
            if (response.getStatus() == 200) {
                if (jsonArraySex.isEmpty()) {
                    jsonResponse.put("message", "Género: " + id + " no se encuentra registrado");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response SexFindByDescription(JsonObject jsonDataSex) {
        try {
            String description = jsonDataSex.getString("description");
            Gender gender = sexRepository.sexFindByDescription(description);
            if(gender == null){
                jsonResponse.put("message", "Género: " + description + " no se encuentra registrado");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArraySex = new JsonObject(Json.encode(gender));
            Response response = Response.ok(jsonArraySex).build();
            if (response.getStatus() == 200) {
                if (jsonArraySex.isEmpty()) {
                    jsonResponse.put("message", "Género: " + description + " no se encuentra registrado");
                    return Response.ok(jsonResponse).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response citySave(JsonObject jsonDataSex) {
        try {
            Gender gender = new Gender();
            gender.description = jsonDataSex.getString("description");
            gender.dateCreate = new Date();
            sexRepository.sexSave(gender);
            JsonObject jsonResponseCreateSex = new JsonObject();
            jsonResponseCreateSex.put("message", "Género " + jsonDataSex.getString("description") + " registrado correctamente");
            return Response.ok(jsonResponseCreateSex).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response SexDelete(JsonObject jsonDataCity) {
        try {
            JsonObject jsonResponseDeleteSex = new JsonObject();
            long id = Long.parseLong(jsonDataCity.getString("id"));
            long responseDelete = sexRepository.sexDelete(id);

            if (responseDelete <= 0) {
                jsonResponse.put("message", "Género " + jsonDataCity.getString("description") + " no se encuentra registrado");
                return Response.ok(jsonResponse).build();
            }
            jsonResponseDeleteSex.put("message", "Género " + jsonDataCity.getString("description") + " ha sido eliminada correctamente");
            return Response.ok(jsonResponseDeleteSex).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response SexUpdate(JsonObject jsonDataSex) {
        try {
            JsonObject jsonResponseSexUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataSex.getString("id"));
            String description = jsonDataSex.getString("description");
            if (id <= 0 || description == null) {
                jsonResponse.put("message", "Género " + jsonDataSex.getString("description") + " no se encuentra registrado");
                return Response.ok(jsonResponse).build();
            }
            Gender gender = sexRepository.sexFindById(id);
            gender.description = description.toUpperCase();
            gender.dateUpdate = new Date();
            sexRepository.sexUpdate(gender);
            jsonResponseSexUpdate.put("message", "Género " + description + " ha sido actualizada correctamente");
            Response response = Response.ok(jsonResponseSexUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
