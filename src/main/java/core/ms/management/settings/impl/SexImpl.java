package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.Sex;
import core.ms.management.settings.dao.repository.SexRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SexImpl {

    @Inject
    SexRepository sexRepository;

    JsonObject jsonResponseFail = new JsonObject();

    ObjectMapper mapper = new ObjectMapper();

    public Response SexListAll() {
        try {
            List<Sex> SexListAll = sexRepository.sexListAll();
            JsonArray jsonArrayListAll = new JsonArray(SexListAll);
            Response response = Response.ok(jsonArrayListAll).build();

            if (response.getStatus() == 200) {
                if (SexListAll.isEmpty()) {
                    jsonResponseFail.put("message", "LIST SEX IS EMPTY");
                    return Response.ok(jsonResponseFail).build();
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
            Sex sex = sexRepository.sexFindById(id);
            if(sex == null){
                jsonResponseFail.put("message", "SEX  BY ID: " + id + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArraySex = new JsonObject(mapper.writeValueAsString(sex));
            Response response = Response.ok(jsonArraySex).build();
            if (response.getStatus() == 200) {
                if (jsonArraySex.isEmpty()) {
                    jsonResponseFail.put("message", "SEX  BY ID: " + id + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
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
            Sex sex = sexRepository.sexFindByDescription(description);
            if(sex == null){
                jsonResponseFail.put("message", "SEX  BY NAME: " + description.toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            JsonObject jsonArraySex = new JsonObject(mapper.writeValueAsString(sex));
            Response response = Response.ok(jsonArraySex).build();
            if (response.getStatus() == 200) {
                if (jsonArraySex.isEmpty()) {
                    jsonResponseFail.put("message", "SEX  BY NAME: " + description.toUpperCase() + " NOT EXISTS");
                    return Response.ok(jsonResponseFail).build();
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
            Sex sex = new Sex();
            sex.setDescription(jsonDataSex.getString("description").toUpperCase());
            sexRepository.sexSave(sex);
            JsonObject jsonResponseCreateSex = new JsonObject();
            jsonResponseCreateSex.put("message", "SEX " + jsonDataSex.getString("description").toUpperCase() + " CREATED");
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
                jsonResponseFail.put("message", "SEX " + jsonDataCity.getString("description").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            jsonResponseDeleteSex.put("message", "SEX " + jsonDataCity.getString("description").toUpperCase() + " DELETE");
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
                jsonResponseFail.put("message", "SEX " + jsonDataSex.getString("description").toUpperCase() + " NOT EXISTS");
                return Response.ok(jsonResponseFail).build();
            }
            Sex sex = sexRepository.sexFindById(id);
            sex.setDescription(description.toUpperCase());
            sexRepository.sexUpdate(sex);
            jsonResponseSexUpdate.put("message", "SEX " + description.toUpperCase() + " HAS UPDATE");
            Response response = Response.ok(jsonResponseSexUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
