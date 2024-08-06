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

    JsonObject jsonResponse = new JsonObject();

    public Response clientCategoryListAll(){
        try{
            JsonArray jsonClientCategory = new JsonArray(clientCategoryRepository.clientCategoryListAll());
            if(jsonClientCategory.isEmpty()){
                jsonResponse.put("message", "No existen registros de categoría de clientes");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonClientCategory).build();
            if(response.getStatus() == 200){
                return Response.ok(response.getEntity()).build();
            }
        }catch (Exception e){
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategoryFindById(JsonObject jsonDataClientCategory) {
        try {
            if(jsonDataClientCategory.isEmpty()){
                return Response.ok(jsonResponse.put("message","No se cuenta con información para realizar la búsqueda")).build();
            }
            long id = Long.parseLong(jsonDataClientCategory.getString("id"));
            if(id > 0){
                ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindById(id);
                if (clientCategory == null) {
                    jsonResponse.put("message", "No existe la información solicitada");
                    return Response.ok(jsonResponse).build();
                }
                JsonObject jsonArrayCityById = new JsonObject(Json.encode(clientCategory));
                Response response = Response.ok(jsonArrayCityById).build();
                if (response.getStatus() == 200) {
                    return Response.ok(response.getEntity()).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategoryFindByDescription(JsonObject jsonDataClientCategory) {
        try {
            if(jsonDataClientCategory.isEmpty()){
                return Response.ok(jsonResponse.put("message","No se cuenta con información para realizar la búsqueda")).build();
            }
            String description = jsonDataClientCategory.getString("description");
            if(!description.isEmpty()){
                ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindByDescription(description);
                if (clientCategory == null) {
                    jsonResponse.put("message", "No existe la información solicitada");
                    return Response.ok(jsonResponse).build();
                }
                JsonObject jsonResponseCityName = new JsonObject(Json.encode(clientCategory));
                Response response = Response.ok(jsonResponseCityName).build();
                if (response.getStatus() == 200) {
                    return Response.ok(response.getEntity()).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response clientCategorySave(JsonObject jsonDataClientCategory) {
        try {
            if(!jsonDataClientCategory.getString("description").isEmpty() && !jsonDataClientCategory.getString("value").isEmpty()){
                ClientCategory clientCategory = new ClientCategory();
                clientCategory.description = jsonDataClientCategory.getString("description");
                clientCategory.value = jsonDataClientCategory.getString("value");
                clientCategory.userCreate = jsonDataClientCategory.getInteger("user_create");
                clientCategory.userUpdate = jsonDataClientCategory.getInteger("user_update");
                clientCategory.dateCreate = new Date();

                clientCategoryRepository.clientCategorySave(clientCategory);
                JsonObject jsonResponseCreateCity = new JsonObject();
                jsonResponseCreateCity.put("message", "Categoría de cliente " + jsonDataClientCategory.getString("description") + " registrada");
                return Response.ok(jsonResponseCreateCity).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo registrar la categoría")).build();
            }
        } catch (Exception e) {
            return Response.accepted(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response clientCategoryDelete(JsonObject jsonDataClientCategory) {
        try {
            if(!jsonDataClientCategory.isEmpty()){
                long id = Long.parseLong(jsonDataClientCategory.getString("id"));
                if(id > 0){
                    long responseDelete = clientCategoryRepository.clientCategoryDelete(id);
                    if (responseDelete <= 0) {
                        jsonResponse.put("message", "Categoría de cliente: " + jsonDataClientCategory.getString("description").toUpperCase() + " ID: "+ jsonDataClientCategory.getString("id") + " no existe");
                        return Response.ok(jsonResponse).build();
                    }
                    jsonResponse.put("message", "Cliente categoría " + jsonDataClientCategory.getString("description").toUpperCase() + " ha sido eliminada");
                    return Response.ok(jsonResponse).build();
                }else{
                    return Response.ok(jsonResponse.put("message","No se pudo eliminar la categoría")).build();
                }
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo eliminar la categoría")).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response clientCategoryUpdate(JsonObject jsonDataClientCategory) {
        try {
            if(jsonDataClientCategory.getLong("id") > 0 && !jsonDataClientCategory.getString("description").isEmpty() && !jsonDataClientCategory.getString("value").isEmpty()){
                JsonObject jsonResponseUpdateClientCategory = new JsonObject();
                ClientCategory clientCategory = new ClientCategory();
                clientCategory.id = jsonDataClientCategory.getLong("id");
                clientCategory.value = jsonDataClientCategory.getString("value");
                clientCategory.description = jsonDataClientCategory.getString("description");
                clientCategoryRepository.clientCategoryUpdate(clientCategory);
                jsonResponseUpdateClientCategory.put("message", "Categoría de cliente " + jsonDataClientCategory.getString("description").toUpperCase() + " ha sido actualizada");
                Response response = Response.ok(jsonResponseUpdateClientCategory).build();
                return Response.ok(response.getEntity()).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo actualizar la categoría")).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }
}
