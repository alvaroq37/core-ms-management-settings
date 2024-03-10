package core.ms.management.settings.impl;

import core.ms.management.settings.dao.entity.City;
import core.ms.management.settings.dao.entity.ClientCategory;
import core.ms.management.settings.dao.entity.Department;
import core.ms.management.settings.dao.repository.ClientCategoryRepository;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ClientCategoryImpl {

    @Inject
    ClientCategoryRepository clientCategoryRepository;

    public Response clientCategoryListAll(){
        try{
            List<ClientCategory> clientCategoryList = clientCategoryRepository.clientCategoryListAll();
            JsonArray jsonClientCategory = new JsonArray(clientCategoryList);
            Response response = Response.ok(jsonClientCategory).build();
            if(response.getStatus() == 200){
                if(clientCategoryList.isEmpty()){
                    JsonObject jsonResponseCityAll = new JsonObject();
                    jsonResponseCityAll.put("message", "No existe información registrada");
                    response = Response.ok(jsonResponseCityAll).build();
                }
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategoryFindById(JsonObject jsonDataClientCategory) {
        try {
            JsonObject jsonClientCategory = new JsonObject();
            long id = Long.parseLong(jsonDataClientCategory.getString("id"));
            ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindById(id);
            if (clientCategory == null) {
                jsonClientCategory.put("message", "No existe la información solicitada");
                return Response.ok(jsonClientCategory).build();
            }
            JsonObject jsonArrayCityById = new JsonObject(Json.encode(clientCategory));
            if (jsonArrayCityById.isEmpty()) {
                jsonClientCategory.put("message", "No existe la información solicitada");
                return Response.ok(jsonClientCategory).build();
            }
            Response response = Response.ok(jsonArrayCityById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategoryFindByDescription(JsonObject jsonDataClientCategory) {
        try {
            JsonObject jsonCityName = new JsonObject();
            String description = jsonDataClientCategory.getString("description");
            ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindByDescription(description);
            if (clientCategory == null) {
                jsonCityName.put("message", "No existe la información solicitada");
                return Response.ok(jsonCityName).build();
            }
            JsonObject jsonResponseCityName = new JsonObject(Json.encode(clientCategory));
            if (jsonResponseCityName.isEmpty()) {
                jsonCityName.put("message", "No existe la información solicitada");
                return Response.ok(jsonCityName).build();
            }
            Response response = Response.ok(jsonResponseCityName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategorySave(JsonObject jsonDataClientCategory) {
        try {

            ClientCategory clientCategory = new ClientCategory();
            clientCategory.description = jsonDataClientCategory.getString("description");
            clientCategory.value = jsonDataClientCategory.getString("value");
            clientCategory.user_create = jsonDataClientCategory.getInteger("user_create");
            clientCategory.userUpdate = jsonDataClientCategory.getInteger("user_update");
            clientCategory.dateCreate = new Date();

            clientCategoryRepository.clientCategorySave(clientCategory);
            JsonObject jsonResponseCreateCity = new JsonObject();
            jsonResponseCreateCity.put("message", "Categoría de client " + jsonDataClientCategory.getString("value") + " registrada");
            return Response.ok(jsonResponseCreateCity).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response clientCategoryDelete(JsonObject jsonDataClientCategory) {
        try {
            JsonObject jsonResponseDeleteClientCategory = new JsonObject();
            long id = Long.parseLong(jsonDataClientCategory.getString("id"));
            long responseDelete = clientCategoryRepository.clientCategoryDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteClientCategory.put("message", "Categoría de cliente: " + jsonDataClientCategory.getString("description").toUpperCase() + " ID: "+ jsonDataClientCategory.getString("id") + " no existe");
                return Response.ok(jsonResponseDeleteClientCategory).build();
            }
            jsonResponseDeleteClientCategory.put("message", "Cliente categoría " + jsonDataClientCategory.getString("description").toUpperCase() + " ha sido eliminado");
            return Response.ok(jsonResponseDeleteClientCategory).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response clientCategoryUpdate(JsonObject jsonDataClientCategory) {
        try {
            JsonObject jsonResponseUpdateClientCategory = new JsonObject();
            ClientCategory clientCategory = new ClientCategory();
            clientCategory.id = jsonDataClientCategory.getLong("id");
            clientCategory.value = jsonDataClientCategory.getString("value");
            clientCategory.description = jsonDataClientCategory.getString("description");
            clientCategoryRepository.clientCategoryUpdate(clientCategory);
            jsonResponseUpdateClientCategory.put("message", "Categoría de client " + jsonDataClientCategory.getString("description").toUpperCase() + " ha sido actualizado");
            Response response = Response.ok(jsonResponseUpdateClientCategory).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
