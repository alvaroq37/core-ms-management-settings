package core.ms.management.settings.impl;

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

@ApplicationScoped
public class UserImpl {

    @Inject
    UserRepository userRepository;

    @Inject
    OccupationRepository occupationRepository;

    @Inject
    CityRepository cityRepository;

    @Inject
    SexRepository sexRepository;

    @Inject
    AgencyRepository agencyRepository;

    JsonObject jsonResponse = new JsonObject();

    public Response userListAll() {
        try {
            JsonArray jsonUser = new JsonArray(userRepository.agencyListAll());
            if (jsonUser.isEmpty()) {
                jsonResponse.put("message", "No existen usuarios registrados");
                return Response.ok(jsonResponse).build();
            }
            Response response = Response.ok(jsonUser).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response userFindById(JsonObject jsonDataUser) {
        try {
            if(jsonDataUser.isEmpty() && jsonDataUser.getString("id").isEmpty()){
                return Response.ok(jsonResponse.put("message", "No existe información suficiente para realizar la búsqueda")).build();
            }
            long id = Long.parseLong(jsonDataUser.getString("id"));
            if(id <= 0){
                return Response.ok(jsonResponse.put("message", "No existe información suficiente para realizar la búsqueda")).build();
            }
            User user = userRepository.userFindById(id);
            if (user == null) {
                jsonResponse.put("message", "No existe el usuario solicitado");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonArrayUserById = new JsonObject(Json.encode(user));
            Response response = Response.ok(jsonArrayUserById).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response userFindByName(JsonObject jsonDataUser) {
        try {
            if(jsonDataUser.isEmpty() && jsonDataUser.getString("email").isEmpty() && jsonDataUser.getString("password").isEmpty()){
                return Response.ok(jsonResponse.put("message", "No existe información suficiente para realizar la búsqueda")).build();
            }
            String email = jsonDataUser.getString("email");
            String password = jsonDataUser.getString("password");
            if(email.isEmpty() && password.isEmpty()){
                return Response.ok(jsonResponse.put("message", "No existe información suficiente para realizar la búsqueda")).build();
            }
            User user = userRepository.userFindByName(email, password);
            if (user == null) {
                jsonResponse.put("message", "No existe el usuario solicitado");
                return Response.ok(jsonResponse).build();
            }
            JsonObject jsonResponseUserName = new JsonObject(Json.encode(user));
            Response response = Response.ok(jsonResponseUserName).build();
            if (response.getStatus() == 200) {
                return Response.ok(response.getEntity()).build();
            }
        } catch (Exception e) {
            return Response.ok(jsonResponse.put("message",e.getMessage())).build();
        }
        return Response.serverError().build();
    }

    public Response userSave(JsonObject jsonDataUser) {
        try {
            if(jsonDataUser.isEmpty() && jsonDataUser.getJsonObject("occupation").isEmpty() && jsonDataUser.getJsonObject("gender").isEmpty() && jsonDataUser.getJsonObject("city").isEmpty() && jsonDataUser.getJsonObject("agency").isEmpty()){
                return Response.ok(jsonResponse.put("message","No existe información suficiente para realizar a búsqueda")).build();
            }
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            JsonObject jsonOccupation = jsonDataUser.getJsonObject("occupation");
            JsonObject jsonGender = jsonDataUser.getJsonObject("gender");
            JsonObject jsonCity = jsonDataUser.getJsonObject("city");
            JsonObject jsonAgency = jsonDataUser.getJsonObject("agency");

            long idOccupation = jsonOccupation.getLong("id");
            long idCity = jsonCity.getLong("id");
            long idGender = jsonGender.getLong("id");
            long idAgency = jsonAgency.getLong("id");

            if(idAgency <= 0 && idCity <= 0 && idGender <= 0 && idOccupation <= 0){
                return Response.ok(jsonResponse.put("message","No existe información suficiente para realizar a búsqueda")).build();
            }
            City city = cityRepository.cityFindById(idCity);
            Occupation occupation = occupationRepository.occupationFindById(idOccupation);
            Gender gender = sexRepository.sexFindById(idGender);
            Agency agency = agencyRepository.agencyFindById(idAgency);

            if(city == null && occupation == null && gender == null && agency == null){
                return Response.ok(jsonResponse.put("message","No existe información suficiente para realizar a búsqueda")).build();
            }

            User user = new User();
            user.ci = jsonDataUser.getString("ci");
            user.names = jsonDataUser.getString("names");
            user.lastNamesPaternal = jsonDataUser.getString("lastNamesPaternal");
            user.lastNamesMaternal = jsonDataUser.getString("lastNamesMaternal");
            user.address = jsonDataUser.getString("address");
            user.cellPhone = Integer.parseInt(jsonDataUser.getString("cellPhone"));
            user.phone = Integer.parseInt(jsonDataUser.getString("phone"));
            user.email = jsonDataUser.getString("email");
            user.dateBirth = formatDate.parse(jsonDataUser.getString("dateBirth"));
            user.password = jsonDataUser.getString("password");
            user.dateCreate = new Date();
            user.city = city;
            user.occupation = occupation;
            user.gender = gender;
            user.agency = agency;

            userRepository.userSave(user);

            JsonObject jsonResponseCreateAgency = new JsonObject();
            jsonResponseCreateAgency.put("message", "Usuario " + jsonDataUser.getString("names") + " registrado(a)");
            return Response.ok(jsonResponseCreateAgency).build();
        } catch (Exception e) {
            return Response.accepted(e.getMessage()).build();
        }
    }

    public Response userDelete(JsonObject jsonDataUser) {
        try {
            JsonObject jsonResponseDeleteUser = new JsonObject();
            long id = Long.parseLong(jsonDataUser.getString("id"));
            long responseDelete = userRepository.agencyDelete(id);

            if (responseDelete <= 0) {
                jsonResponseDeleteUser.put("message", "Usuario: " + jsonDataUser.getString("names") + " ID: "+ jsonDataUser.getString("id") + " no existe");
                return Response.ok(jsonResponseDeleteUser).build();
            }
            jsonResponseDeleteUser.put("message", "Usuario " + jsonResponseDeleteUser.getString("names") + " ha sido eliminado(a)");
            return Response.ok(jsonResponseDeleteUser).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }

    public Response userUpdate(JsonObject jsonDataUser) {
        try {
            JsonObject jsonResponseUpdateAgency= new JsonObject();
            User user = new User();
            user.id = jsonDataUser.getLong("id");
            user.ci = jsonDataUser.getString("ci");
            user.names = jsonDataUser.getString("names");
            user.lastNamesPaternal = jsonDataUser.getString("last_name_paternal");
            user.lastNamesMaternal = jsonDataUser.getString("last_name_maternal");
            user.address = jsonDataUser.getString("address");
            user.cellPhone = jsonDataUser.getInteger("cell_phone");
            user.phone = jsonDataUser.getInteger("phone");
            user.email = jsonDataUser.getString("email");
            user.dateBirth = new Date();

            userRepository.userUpdate(user);
            jsonResponseUpdateAgency.put("message", "Usuario " + jsonResponseUpdateAgency.getString("names").toUpperCase() + " ha sido actualizado(a) ");
            Response response = Response.ok(jsonResponseUpdateAgency).build();
            return Response.ok(response.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
