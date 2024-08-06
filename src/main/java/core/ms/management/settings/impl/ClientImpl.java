package core.ms.management.settings.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ms.management.settings.dao.entity.*;
import core.ms.management.settings.dao.repository.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ClientImpl {

    @Inject
    ClientRepository clientRepository;

    @Inject
    CityRepository cityRepository;

    @Inject
    SexRepository sexRepository;

    @Inject
    OccupationRepository occupationRepository;

    @Inject
    ClientCategoryRepository clientCategoryRepository;

    JsonObject jsonResponse = new JsonObject();

    ObjectMapper mapper = new ObjectMapper();
    public Response clientListAll() {
        try {
            JsonArray jsonArrayClientAll = new JsonArray(clientRepository.clientListAll());
            if (jsonArrayClientAll.isEmpty()) {
                jsonResponse.put("message", "No existen clientes registrados");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonArrayClientAll).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se han podido listar los clientes")).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response clientFindByCi(JsonObject jsonDataClient) {
        try {
            if(!jsonDataClient.isEmpty() && !jsonDataClient.getString("ci").isEmpty()){
                String ci = jsonDataClient.getString("ci");
                if(!ci.isEmpty()){
                    Client clientFindByCi = clientRepository.clientFindByCi(ci);
                    if(clientFindByCi == null){
                        return Response.ok(jsonResponse.put("message","No se encontró al cliente con número de documento: " + ci)).build();
                    }
                    JsonObject jsonArrayClientCi = new JsonObject(Json.encode(clientFindByCi));
                    Response response = Response.ok(jsonArrayClientCi).build();
                    if (response.getStatus() == 200) {
                        return Response.ok(response.getEntity()).build();
                    }
                }else{
                    return Response.ok(jsonResponse.put("message","No se ingresó un número de documento valido")).build();
                }
            }else{
                return Response.ok(jsonResponse.put("message","No existe información suficiente para realizar la búsqueda")).build();
            }
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.serverError().build();
    }

    public Response clientFindById(JsonObject jsonDataClient) {
        try {
            if(!jsonDataClient.isEmpty() && jsonDataClient.getString("id").isEmpty()){
                long id = Long.parseLong(jsonDataClient.getString("id"));
                if(id > 0){
                    Client clientFindById = clientRepository.clientFindById(id);
                    if(clientFindById == null){
                        return Response.ok(jsonResponse.put("message","No se encontró al cliente")).build();
                    }
                    JsonObject jsonArrayClientById = new JsonObject(Json.encode(clientFindById));
                    Response response = Response.ok(jsonArrayClientById).build();
                    if (response.getStatus() == 200) {
                        return Response.ok(response.getEntity()).build();
                    }else{
                        return Response.ok().build();
                    }
                }
            }else{
                return Response.ok().build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response clientFindByName(JsonObject jsonDataClient) {
        try {
            if(!jsonDataClient.isEmpty() && jsonDataClient.getString("name").isEmpty()){
                String name = jsonDataClient.getString("name");
                Client clientFindByName = clientRepository.clientFindByName(name);
                if (clientFindByName == null) {
                    jsonResponse.put("message", "Cliente con nombre: " + name + " no se encuentra registrado");
                    return Response.ok(jsonResponse).build();
                }
                JsonObject jsonArrayClient = new JsonObject(Json.encode(clientFindByName));
                Response response = Response.ok(jsonArrayClient).build();
                if (response.getStatus() == 200) {
                    return Response.ok(response.getEntity()).build();
                }
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response clientSave(JsonObject jsonDataClient) {
        try {
            if(jsonDataClient.isEmpty() && jsonDataClient.getJsonObject("occupation").isEmpty() && jsonDataClient.getJsonObject("gender").isEmpty() && jsonDataClient.getJsonObject("city").isEmpty() && jsonDataClient.getJsonObject("client_category").isEmpty()){
                return Response.ok(jsonResponse.put("message","No se cuenta con información suficiente para el registro")).build();
            }
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");

            JsonObject jsonOccupation = jsonDataClient.getJsonObject("occupation");
            JsonObject jsonGender = jsonDataClient.getJsonObject("gender");
            JsonObject jsonCity = jsonDataClient.getJsonObject("city");
            JsonObject jsonClientCategory = jsonDataClient.getJsonObject("client_category");

            long idOccupation = jsonOccupation.getLong("id");
            long idCity = jsonCity.getLong("id");
            long idGender = jsonGender.getLong("id");
            long idCategory = jsonClientCategory.getLong("id");

            if(idOccupation > 0 && idCity > 0 && idGender > 0 && idCategory > 0){
                City city = cityRepository.cityFindById(idCity);
                Gender gender = sexRepository.sexFindById(idGender);
                Occupation occupation = occupationRepository.occupationFindById(idOccupation);
                ClientCategory clientCategory = clientCategoryRepository.clientCategoryFindById(idCategory);

                if(city==null && gender == null && occupation == null && clientCategory == null){
                    return Response.ok(jsonResponse.put("message","No se puede registrar al cliente")).build();
                }
                String dateHappy = jsonDataClient.getString("dateBirth");
                Client client = new Client();
                client.ci = jsonDataClient.getString("ci");
                client.names = jsonDataClient.getString("names").toUpperCase();
                client.email = jsonDataClient.getString("email");
                client.cellPhone = Integer.parseInt(jsonDataClient.getString("number_cell_phone"));
                client.dateBirth = formatDate.parse(dateHappy);
                client.lastNamesMaternal = jsonDataClient.getString("names_maternal").toUpperCase();
                client.lastNamesPaternal = jsonDataClient.getString("names_paternal").toUpperCase();
                client.zone = jsonDataClient.getString("zone");
                client.issued = jsonDataClient.getString("issued");
                client.streetAvenue = jsonDataClient.getString("street_avenue");
                client.phone = Integer.parseInt(jsonDataClient.getString("number_phone"));
                client.dateCreate = new Date();
                client.city = city;
                client.gender = gender;
                client.occupation = occupation;
                client.clientCategory = clientCategory;

                clientRepository.clientSave(client);
                JsonObject jsonResponseClientSave = new JsonObject();
                jsonResponseClientSave.put("message", "Cliente " + jsonDataClient.getString("names") + " registrado");
                return Response.ok(jsonResponseClientSave).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se puede registrar al cliente")).build();
            }
        } catch (Exception e) {
            return Response.accepted(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response clientDelete(JsonObject jsonDataClient) {
        try {
            if(jsonDataClient.isEmpty() && jsonDataClient.getString("id").isEmpty()){
                return Response.ok(jsonResponse.put("message", "No existe información suficiente para realizar la eliminación del cliente")).build();
            }
            JsonObject jsonResponseClientDelete = new JsonObject();
            long id = Long.parseLong(jsonDataClient.getString("id"));
            if(id > 0){
                long responseDelete = clientRepository.clientDelete(id);
                if (responseDelete <= 0) {
                    jsonResponse.put("message", "Cliente " + jsonDataClient.getString("name") + " no se encuentra registrado");
                    return Response.ok(jsonResponse).build();
                }
                jsonResponseClientDelete.put("message", "Cliente " + jsonDataClient.getString("name") + " ha sido eliminado correctamente");
                return Response.ok(jsonResponseClientDelete).build();
            }else{
                return Response.ok(jsonResponse.put("message","No se pudo eliminar al cliente")).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
    }

    public Response clientUpdate(JsonObject jsonDataClient) {
        try {
            JsonObject jsonResponseClientUpdate = new JsonObject();
            long id = Long.parseLong(jsonDataClient.getString("id"));
            String name = jsonDataClient.getString("names");
            if (id <= 0 || name == null) {
                jsonResponse.put("message", "Cliente " + jsonDataClient.getString("names") + " no se encuentra registrado");
                return Response.ok(jsonResponse).build();
            }
            long idCity = Long.parseLong(jsonDataClient.getString("idCity"));
            long idSex = Long.parseLong(jsonDataClient.getString("idSex"));
            long idOccupation = Long.parseLong(jsonDataClient.getString("idOccupation"));

            if(idCity <= 0 && idSex <= 0 && idOccupation <= 0){
                return Response.ok(jsonResponse.put("message","No se puede actualizar al cliente")).build();
            }

            City city = cityRepository.cityFindById(idCity);
            Gender gender = sexRepository.sexFindById(idSex);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);

            if(city == null && gender == null && occupation == null){
                return Response.ok(jsonResponse.put("message","No se puede actualizar al cliente")).build();
            }

            Client client = clientRepository.clientFindById(Long.parseLong(jsonDataClient.getString("id")));
            if(client == null){
                return Response.ok(jsonResponse.put("message","No se puede actualizar al cliente")).build();
            }
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            String dateHappy = jsonDataClient.getString("dateBirth");
            client.ci = jsonDataClient.getString("ci");
            client.names = jsonDataClient.getString("names").toUpperCase();
            client.email = jsonDataClient.getString("email");
            client.cellPhone = Integer.parseInt(jsonDataClient.getString("number_cell_phone"));
            client.dateBirth = formatDate.parse(dateHappy);
            client.lastNamesMaternal = jsonDataClient.getString("names_maternal").toUpperCase();
            client.lastNamesPaternal = jsonDataClient.getString("names_paternal").toUpperCase();
            client.phone = Integer.parseInt(jsonDataClient.getString("number_phone"));
            client.city = city;
            client.gender = gender;
            client.occupation = occupation;
            client.dateUpdate = new Date();

            clientRepository.clientUpdate(client);
            jsonResponseClientUpdate.put("message", "Cliente " + name + " ha sido actualizado correctamente");
            Response response = Response.ok(jsonResponseClientUpdate).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
